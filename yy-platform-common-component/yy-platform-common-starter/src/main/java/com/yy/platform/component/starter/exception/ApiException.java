package com.yy.platform.component.starter.exception;

public class ApiException extends RuntimeException {
    private String code;
    private String msg;

    public ApiException() {
        this("400","接口错误");
    }

    public ApiException(String msg) {
        this("400",msg);
    }

    public ApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
