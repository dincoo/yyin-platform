package com.yy.platform.component.starter.web.resolver;

import com.yy.platform.component.starter.constants.CommonConstant;
import com.yy.platform.component.starter.constants.ErrorCodeEnum;
import com.yy.platform.component.starter.exception.ApiException;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.auth.model.ISysUser;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.component.starter.web.shiro.TokenSubjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.yy.platform.component.starter.web.shiro.filter.HmacTokenFilter.LOGIN_TOKEN_KEY;

@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    final TokenSubjectUtil tokenSubjectUtil;

    public LoginUserArgumentResolver(TokenSubjectUtil tokenSubjectUtil) {
        this.tokenSubjectUtil = tokenSubjectUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class) &&  methodParameter.getParameterType().isAssignableFrom(LoginUserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);
        boolean isFull = loginUser.isFull();
        boolean isTest = loginUser.isTest();

        String token = nativeWebRequest.getHeader(LOGIN_TOKEN_KEY);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = nativeWebRequest.getParameter(LOGIN_TOKEN_KEY);
        }
        if(StringUtils.isBlank(token)){
            throw new ApiException("400","无token,非法请求");
        }
        //String host = nativeWebRequest.host
        //从token中解析userId
        String userId = JwtTokenUtil.getUserIdFromToken(token);

        if(null == userId){
            if(isTest){
                ISysUser testUser = new ISysUser();
                testUser.setId(CommonConstant.TEST_USER_ID);
                return testUser;
            }
            throw new AuthException(ErrorCodeEnum.AUTH_TOKEN_ERROR.getCode(), ErrorCodeEnum.AUTH_TOKEN_ERROR.getDesc());
        }
        LoginUserInfo userDetail = null;
        //ISysUser sysUser = null;
        if(isFull){

        }else{
            userDetail = tokenSubjectUtil.getUser(userId);
            if(userDetail == null){
                throw new AuthException(ErrorCodeEnum.AUTH_TOKEN_ERROR.getCode(), ErrorCodeEnum.AUTH_TOKEN_ERROR.getDesc());
            }
            //sysUser = new ISysUser();
            //sysUser.setId(userId);
        }

        return userDetail;
    }
}
