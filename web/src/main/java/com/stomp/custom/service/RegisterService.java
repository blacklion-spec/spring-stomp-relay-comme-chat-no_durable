package com.stomp.custom.service;

import com.stomp.custom.access.PersistenceAccessFactory;
import com.stomp.custom.access.cache.CacheAccessFactory;
import com.stomp.custom.access.cache.CacheAccessor;
import com.stomp.custom.constant.MailConstant;
import com.stomp.custom.dto.RegisterDto;
import com.stomp.custom.entity.User;
import com.stomp.custom.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @createTime 2022年08月21日 22:13:00
 */
@Service
public class RegisterService {

    private final PersistenceAccessFactory persistenceAccessFactory;


    private final CacheAccessFactory cacheAccessFactory;

    public RegisterService(PersistenceAccessFactory persistenceAccessFactory, CacheAccessFactory cacheAccessFactory) {
        this.persistenceAccessFactory = persistenceAccessFactory;
        this.cacheAccessFactory = cacheAccessFactory;
    }

    public Response doRegister(RegisterDto registerDto, HttpServletResponse response) {
        String userMail = registerDto.getUserMail();
        User oldUser = persistenceAccessFactory.createUserAccess().getByMail(userMail);
        if (oldUser != null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new Response().setSuccess(false).setMsg("用户已存在");
        }
        CacheAccessor cacheAccessor = cacheAccessFactory.createCacheAccessor();
        String redisVerifiCode = cacheAccessor.getVerificationCode(MailConstant.VERIFI_CODE_PREFIX + registerDto.getUserMail());
        if (StringUtils.isEmpty(redisVerifiCode) || !redisVerifiCode.equals(registerDto.getVerificationCode())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new Response().setSuccess(false).setMsg("验证码错误");
        }
        User user = registerDto.toUser();
        if (persistenceAccessFactory.createUserAccess().insert(user)) {
            return new Response().setSuccess(true).setMsg("注册成功");
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Response().setSuccess(true).setMsg("注册失败");
        }
    }

}
