package com.stomp.custom.service;

import com.stomp.custom.access.PersistenceAccessFactory;
import com.stomp.custom.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @createTime 2022年08月13日 00:19:00
 */
@Service
public class UserService implements IUserService{

    private final PersistenceAccessFactory accessFactory;

    public UserService(PersistenceAccessFactory accessFactory) {
        this.accessFactory = accessFactory;
    }

    public User getByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return accessFactory.createUserAccess().getByUsername(username);
    }

    public User getByMail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            return null;
        }
        return accessFactory.createUserAccess().getByMail(mail);
    }

}
