package com.stomp.custom.service;

import com.stomp.custom.MailUtil;
import com.stomp.custom.VerifiUtil;
import com.stomp.custom.access.PersistenceAccessFactory;
import com.stomp.custom.access.cache.CacheAccessFactory;
import com.stomp.custom.constant.MailConstant;
import com.stomp.custom.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @createTime 2022年08月21日 14:40:00
 */
@Service
public class MailHandleServiceImpl implements IMailHandleService {

    private final PersistenceAccessFactory accessFactory;

    private final IMailService mailService;

    private final CacheAccessFactory cacheAccessFactory;

    private final RedisTemplate<String, Object> redisTemplate;

    public MailHandleServiceImpl(PersistenceAccessFactory accessFactory, IMailService mailService, RedisTemplate<String, Object> redisTemplate, CacheAccessFactory cacheAccessFactory) {
        this.accessFactory = accessFactory;
        this.mailService = mailService;
        this.redisTemplate = redisTemplate;
        this.cacheAccessFactory = cacheAccessFactory;
    }


    public Response sendVerCodeForMail(String mail, HttpServletResponse response) {
        if (!MailUtil.validationMail(mail)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new Response().setSuccess(false).setMsg("邮箱格式不正确");
        }
        if (accessFactory.createUserAccess().getByMail(mail) != null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new Response().setSuccess(false).setMsg("邮箱已经存在");
        }
        String verifCode = VerifiUtil.createVerifCode();
        cacheAccessFactory.createCacheAccessor().putVerificationCode(MailConstant.VERIFI_CODE_PREFIX + mail,verifCode);
        mailService.sendSimpleMail(mail, "公共聊天室验证码", "您的验证码是：" + verifCode + "。" + "\r\n" + "请在三分钟内使用");
        return new Response().setSuccess(true).setMsg("发送成功");
    }
}
