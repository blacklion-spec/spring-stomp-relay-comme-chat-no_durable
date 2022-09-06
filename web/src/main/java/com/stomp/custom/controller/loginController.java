package com.stomp.custom.controller;

import com.stomp.custom.constant.CookieName;
import com.stomp.custom.service.ILoginService;
import com.stomp.custom.token.JwtTokenCreator;
import com.stomp.custom.transfer.LoginTransfer;
import com.stomp.custom.vo.LoginVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @createTime 2022年09月02日 15:26:00
 */
@Controller
public class loginController {

    public final static String LOGIN_MAPPING = "/login";
    private final ILoginService loginService;

    public loginController(ILoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping(LOGIN_MAPPING)
    public String login(@ModelAttribute LoginVo loginVo, HttpServletResponse response) {
        LoginTransfer loginTransfer = loginService.doLogin(loginVo);
        switch (loginTransfer.getLoginResult()) {
            case PARAMETER_ERROR:
                response.setStatus(400);
                break;
            case LOGIN_SUCCESS:
                setCookie(response);
                setCookie(response, loginTransfer.getAttachment());
                return "redirect:" + ChatController.CHAT_INDEX_MAPPING;
        }
        return "redirect:" + IndexController.INDEX_MAPPING;
    }


    private void setCookie(HttpServletResponse response) {
        JwtTokenCreator jwtTokenCreator = new JwtTokenCreator();
        String token = jwtTokenCreator.creatorToken(null);
        Cookie cookie = new Cookie(CookieName.JWT_COOKIE_NAME, token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    private void setCookie(HttpServletResponse response, Map<String, String> attachment) {
        Optional<Map<String, String>> optional = Optional.ofNullable(attachment);
        if (optional.isPresent()) {
            attachment.forEach((k, v) -> {
                if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
                    Cookie cookie = new Cookie(k, v);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            });
        }
    }
}
