package com.stomp.custom.entity;

import lombok.Data;

import java.util.Date;

/**
 * @createTime 2022年08月11日 22:59:00
 */
@Data
public class User {

    private Long userId;

    private String userMail;

    private String userName;

    private String password;

    private Date createTime;

    private Byte isBan;

}
