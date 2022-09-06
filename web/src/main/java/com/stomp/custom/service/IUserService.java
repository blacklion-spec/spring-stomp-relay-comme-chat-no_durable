package com.stomp.custom.service;

import com.stomp.custom.entity.User;

/**
 * @createTime 2022年09月02日 15:29:00
 */
public interface IUserService {

     User getByUsername(String username);

     User getByMail(String mail);

}
