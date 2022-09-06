package com.stomp.custom.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @createTime 2022年09月02日 14:15:00
 */
public class JWTUtil {

    private final static Properties jwtProperties;

    private final static String JWT_ISSUER = "www.littleass.top";

    private final static String JWT_SECRET_PROPERTIES_KEY = "jwt.secret";

    static {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jwt.properties");
        jwtProperties = new Properties();
        try {
            jwtProperties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String createJwt(Map<String, String> payload) {
        Algorithm algorithm = Algorithm.HMAC384(jwtProperties.getProperty(JWT_SECRET_PROPERTIES_KEY));
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(JWT_ISSUER);
        if (payload != null) {
            payload.forEach(builder::withClaim);
        }
        return builder.sign(algorithm);
    }


    public static boolean verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC384(jwtProperties.getProperty(JWT_SECRET_PROPERTIES_KEY)); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            DecodedJWT jwt =  verifier.verify(token);
            return true;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

}
