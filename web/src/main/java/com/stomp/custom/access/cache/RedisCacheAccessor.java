package com.stomp.custom.access.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @createTime 2022年09月02日 17:49:00
 */
@Component
public class RedisCacheAccessor implements CacheAccessor {

    private final RedisTemplate<String, Object> redisTemplate;
    private final int KEY_EXPIRATION_TIME = 3;

    public RedisCacheAccessor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public String getVerificationCode(String key) {
        return (String)redisTemplate.opsForValue().get(key);
    }

    @Override
    public void putVerificationCode(String key, String value) {
        if (Objects.isNull(key) || Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        redisTemplate.opsForValue().set(key, value, KEY_EXPIRATION_TIME, TimeUnit.MINUTES);
    }

}
