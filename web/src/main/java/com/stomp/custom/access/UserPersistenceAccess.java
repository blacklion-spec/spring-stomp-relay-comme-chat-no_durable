package com.stomp.custom.access;

import com.stomp.custom.entity.User;

/**
 * @createTime 2022年09月02日 15:37:00
 */
public interface UserPersistenceAccess {

    User getByUsername(String username);

    User getByMail(String mail);

    boolean insert(User user);

    User getByUserId(long userId);

}
