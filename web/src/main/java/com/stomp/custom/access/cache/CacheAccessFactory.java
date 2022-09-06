package com.stomp.custom.access.cache;

/**
 * @createTime 2022年09月02日 17:36:00
 * 缓存数据库工厂
 */
public interface CacheAccessFactory {

    CacheAccessor createCacheAccessor();

}
