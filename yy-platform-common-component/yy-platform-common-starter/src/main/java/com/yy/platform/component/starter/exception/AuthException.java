package com.yy.platform.component.starter.exception;

import org.springframework.http.HttpStatus;

/**
 * @Auther: zhongdh
 * @Date: 2019/12/20 09:47
 * @Description: 接口认证异常类
 */
public class AuthException extends BaseCustomizeException {

    public AuthException(String errorCode, String errorMessage, String errorPosition, String errorDesc) {
        super(errorCode, errorMessage, errorPosition, errorDesc);
    }

    public AuthException(String errorMessage, String errorPosition) {
        super(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errorMessage, errorPosition, "鉴权失败");
    }
}
