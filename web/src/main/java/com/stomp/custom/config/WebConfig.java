package com.stomp.custom.config;

import com.stomp.custom.controller.loginController;
import com.stomp.custom.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @createTime 2022年08月14日 11:01:00
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeString = Arrays.asList("/",
                "/**/*.css", "/**/*.js", loginController.LOGIN_MAPPING, "/favicon.ico",
                "/register/**", "/mail/**");
        registry.addInterceptor(new AuthorizationInterceptor())
                .excludePathPatterns(excludeString);
    }

}
