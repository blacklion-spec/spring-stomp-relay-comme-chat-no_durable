package com.stomp.custom.token;

import com.stomp.custom.jwt.JWTUtil;

import java.util.Map;

/**
 * @createTime 2022年09月02日 22:07:00
 *
 */
public class JwtTokenCreator implements TokenCreator {

    @Override
    public String creatorToken(Map<String, String> payload) {
        return JWTUtil.createJwt(payload);
    }

    @Override
    public boolean verifyJwt(String token) {
        return JWTUtil.verifyJwt(token);
    }

}
