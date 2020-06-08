package com.yy.platform.component.starter.web.resolver;

import com.yy.platform.component.starter.constants.CommonConstant;
import com.yy.platform.component.starter.constants.ErrorCodeEnum;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.web.auth.SecurityContext;
import com.yy.platform.component.starter.web.auth.model.SysUser;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class) &&  methodParameter.getParameterType().isAssignableFrom(SysUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);
        boolean isFull = loginUser.isFull();
        boolean isTest = loginUser.isTest();
        Long userId = SecurityContext.getUserId();

        if(null == userId){
            if(isTest){
                SysUser testUser = new SysUser();
                testUser.setUserId(CommonConstant.TEST_USER_ID);
                return testUser;
            }
            throw new AuthException(ErrorCodeEnum.AUTH_TOKEN_ERROR.getCode(), ErrorCodeEnum.AUTH_TOKEN_ERROR.getDesc());
        }
        SysUser sysUser = null;
        if(isFull){

        }else{
            sysUser = new SysUser();
            sysUser.setUserId(userId);
        }

        return sysUser;
    }
}
