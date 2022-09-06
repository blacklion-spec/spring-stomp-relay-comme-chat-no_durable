package com.stomp.custom.vo;

/**
 * @createTime 2022年08月21日 14:34:00
 */
public class Response {

    private boolean success;

    private Object result;

    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public Response setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public Response setResult(Object result) {
        this.result = result;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
