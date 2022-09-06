package com.stomp.custom.access;

import com.stomp.custom.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * @createTime 2022年09月02日 15:36:00
 */

@Component
public class MybatisAccessFactory implements PersistenceAccessFactory {

    private final UserMapper userMapper;

    public MybatisAccessFactory(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserPersistenceAccess createUserAccess() {
        return userMapper;
    }

}
