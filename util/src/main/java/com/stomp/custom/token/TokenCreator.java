package com.stomp.custom.token;

import java.util.Map;

/**
 * @createTime 2022年09月02日 22:07:00
 * Token的抽象
 */
public interface TokenCreator {

    String creatorToken(Map<String, String> payload);

    boolean verifyJwt(String token);

}
