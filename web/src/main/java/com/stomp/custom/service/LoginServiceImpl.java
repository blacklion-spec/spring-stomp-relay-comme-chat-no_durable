package com.stomp.custom.service;

import com.stomp.custom.MailUtil;
import com.stomp.custom.constant.LoginResult;
import com.stomp.custom.entity.User;
import com.stomp.custom.transfer.LoginTransfer;
import com.stomp.custom.vo.LoginVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.Element;
import java.util.HashMap;
import java.util.Map;

/**
 * @createTime 2022年09月02日 17:01:00
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private final IUserService userService;

    public LoginServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public LoginTransfer doLogin(LoginVo loginVo) {
        String userMail = loginVo.getUserMail();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(userMail) || StringUtils.isEmpty(password) || !MailUtil.validationMail(userMail)) {
            return new LoginTransfer(LoginResult.PARAMETER_ERROR);
        }else {
            User user = userService.getByMail(userMail.trim());
            if (password.trim().equals(user.getPassword())) {
                Map<String, String> attachment = new HashMap<>();
                attachment.put("username", user.getUserName());
                attachment.put("userKey",user.getUserMail());
                return new LoginTransfer(LoginResult.LOGIN_SUCCESS,attachment) ;
            }
        }
        return new LoginTransfer(LoginResult.LOGIN_FAILURE);
    }

}
