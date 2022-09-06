package com.stomp.custom.access.cache;

/**
 * @createTime 2022年09月02日 17:42:00
 * 访问缓存的抽象
 */
public interface CacheAccessor {

    String getVerificationCode(String key);

    void putVerificationCode(String key,String value);

}
