package com.yy.platform.component.starter.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhongdh
 * @Date: 2019/10/23 10:32
 * @Description: 基础自定义异常
 */

@Getter
@Setter
public class BaseCustomizeException extends RuntimeException {

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

    public BaseCustomizeException(String errorCode, String errorMessage, String errorDesc) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        this.errorPosition = stackTrace[2].getClassName()+"."+stackTrace[2].getMethodName();
        this.errorDesc = errorDesc;
    }

    public BaseCustomizeException(String errorCode, String errorMessage, String errorPosition, String errorDesc) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorPosition = errorPosition;
        this.errorDesc = errorDesc;
    }

    public BaseCustomizeException(Throwable cause, String errorCode, String errorMessage, String errorPosition, String errorDesc) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorPosition = errorPosition;
        this.errorDesc = errorDesc;
    }

    public BaseCustomizeException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMessage, String errorPosition, String errorDesc) {
        super(errorMessage, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorPosition = errorPosition;
        this.errorDesc = errorDesc;
    }

    @Override
    public String toString() {
        return "BaseCustomizeException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorPosition='" + errorPosition + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}
