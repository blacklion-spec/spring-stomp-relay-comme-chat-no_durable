package com.stomp.custom.service;

import com.stomp.custom.vo.Response;

import javax.servlet.http.HttpServletResponse;

/**
 * @createTime 2022年09月02日 13:41:00
 * 邮件逻辑处理接口，责任太大了？
 */
public interface IMailHandleService {

    Response sendVerCodeForMail(String mail, HttpServletResponse response);

}
