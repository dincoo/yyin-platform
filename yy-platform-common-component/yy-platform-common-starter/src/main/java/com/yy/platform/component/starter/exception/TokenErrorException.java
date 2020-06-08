package com.yy.platform.component.starter.exception;

public class TokenErrorException extends RuntimeException  {

    /**
     * 异常代码
     */
    private String errorCode;

    /**
     * 异常信息
     */
    private String errorMessage;

    /**
     * 异常发生的方法或位置
     */
    private String errorPosition;

    /**
     * 异常描述
     */
    private String errorDesc;

}
