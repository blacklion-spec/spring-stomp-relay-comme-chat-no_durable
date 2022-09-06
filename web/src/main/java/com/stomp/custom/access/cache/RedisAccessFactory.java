package com.stomp.custom.access.cache;

import org.springframework.stereotype.Component;

/**
 * @createTime 2022年09月02日 17:38:00
 */
@Component
public class RedisAccessFactory implements CacheAccessFactory {

    private final RedisCacheAccessor redisCacheAccessor;

    public RedisAccessFactory(RedisCacheAccessor redisCacheAccessor) {
        this.redisCacheAccessor = redisCacheAccessor;
    }

    @Override
    public CacheAccessor createCacheAccessor() {
        return redisCacheAccessor;
    }

}
