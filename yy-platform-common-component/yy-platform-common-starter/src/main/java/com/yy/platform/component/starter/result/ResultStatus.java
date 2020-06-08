package com.yy.platform.component.starter.result;

import org.springframework.http.HttpStatus;

/**
 * @Auther: 092506540
 * @Date: 2019/10/22 14:49
 * @Description: 结果状态码映射枚举
 */
public enum ResultStatus {

    SUCCESS(HttpStatus.OK, String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase()),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, String.valueOf(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED.getReasonPhrase()),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    /**
     * 返回的HTTP状态码
     */
    private HttpStatus httpStatus;

    /**
     * 业务异常码
     */
    private String code;

    /**
     * 业务异常信息描述
     */
    private String message;

    ResultStatus(HttpStatus httpStatus, String code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
