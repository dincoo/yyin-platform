package com.yy.platform.component.starter.advices;
import com.yy.platform.component.starter.exception.ApiException;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.exception.BaseCustomizeException;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.result.ResultStatus;
import com.yy.platform.component.starter.result.model.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhongdh
 * @Date: 2019/10/23 11:05
 * @Description: 全局异常处理增强器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    /**
     * 全局自定义异常处理
     * @param bex
     */
    @ExceptionHandler(BaseCustomizeException.class)
    @ResponseBody
    public ResultData baseCustomizeExceptionHandler(HttpServletRequest request, BaseCustomizeException bex){
        String url = request.getRequestURI();
        log.error("异常接口【{}】, 捕获BaseCustomizeException异常:", url, bex);
        if(StringUtils.isBlank(bex.getErrorPosition())){
            bex.setErrorPosition(url);
        }
        return ResultData.failure(bex);
    }

    /**
     * 全局参数校验异常处理
     * @param request
     * @param manex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultData methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException manex){
        String url = request.getRequestURI();
        log.error("异常接口【{}】, 捕获BaseCustomizeException异常:", url, manex);
        return ResultData.failure(manex.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 全局系统异常处理
     * @param e
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData exceptionHandler(HttpServletRequest request, Exception e){
        String url = request.getRequestURI();
        log.error("异常接口【{}】, 捕获Exception异常:", url, e);
        BaseCustomizeException bce = new BaseCustomizeException(e, ResultStatus.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), url, ResultStatus.INTERNAL_SERVER_ERROR.getMessage());
        return ResultData.failure(bce);
    }

    @ExceptionHandler(ApiException.class)
    public ResultData ApiExceptionHandler(ApiException e){
        return ResultData.failure(e.getCode(),"",e.getMsg());
    }

    /**
     * 接口权限异常全局捕获
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public R unauthorizedException(UnauthorizedException e){
        return R.Builder.badReq().message("权限不足，无法操作").build();
    }


    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public R authException(AuthException e){
        return R.Builder.badReq().message("未登录").errorCode(e.getErrorCode()).build();
    }
}
