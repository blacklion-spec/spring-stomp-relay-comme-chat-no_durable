package com.stomp.custom.access;

/**
 * 持久化数据访问工厂
 *
 * @createTime 2022年09月02日 15:35:00
 */
public interface PersistenceAccessFactory {

    UserPersistenceAccess createUserAccess();

}
