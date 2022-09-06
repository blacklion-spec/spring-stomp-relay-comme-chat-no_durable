package com.stomp.custom.interceptor;

import com.stomp.custom.constant.CookieName;
import com.stomp.custom.jwt.JWTUtil;
import com.stomp.custom.token.JwtTokenCreator;
import com.stomp.custom.token.TokenCreator;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @createTime 2022年08月14日 10:45:00
 * 权限鉴定器，判断是否登录
 */
public class AuthorizationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() == null) {
            return false;
        }
        Optional<Cookie> optional = Stream.of(request.getCookies()).filter(e -> CookieName.JWT_COOKIE_NAME.equals(e.getName())).findFirst();
        boolean valid = false;
        if (optional.isPresent()) {
            TokenCreator tokenCreator = new JwtTokenCreator();
            valid = tokenCreator.verifyJwt(optional.get().getValue());
        }
        if (!valid) {
            response.setStatus(302);
            response.setHeader("Location", "/"); //未登录返回到登录页面
        }
        return valid;
    }

}
