package com.stomp.custom.transfer;

import com.stomp.custom.constant.LoginResult;

import java.util.Map;

/**
 * @createTime 2022年09月02日 22:36:00
 */
public class LoginTransfer {

    private final   LoginResult loginResult;

    private Map<String,String>  attachment;


    public LoginTransfer(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public LoginTransfer(LoginResult loginResult, Map<String, String> attachment) {
        this.loginResult = loginResult;
        this.attachment = attachment;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public Map<String, String> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, String> attachment) {
        this.attachment = attachment;
    }
}
