package com.yy.platform.component.starter.web.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yy.platform.component.starter.constants.ErrorCodeEnum;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.web.auth.model.SysUser;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.annotation.IgnoreAuth;
import com.yy.platform.component.starter.web.auth.SecurityContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
    public static final String LOGIN_TOKEN_KEY = "X-JLXshop-Token";


    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,X-Nideshop-Token,X-URL-PATH");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }
//
//        // 检查token并设置
//        long userId = checkToken(request);
//
//        // TODO 监权
//
//        SecurityContext.setUserId(userId);
        return true;
    }


    private long checkToken(HttpServletRequest request) throws AuthException{

        //从header中获取token
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(LOGIN_TOKEN_KEY);
        }

        //token为空
        if (StringUtils.isBlank(token)) {
            throw new AuthException(ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getCode(), ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getDesc());
        }

        try{
            jwtTokenUtil.verifyToken(token);
        }catch (JWTVerificationException e){
            throw new AuthException(ErrorCodeEnum.AUTH_TOKEN_ERROR.getCode(), ErrorCodeEnum.AUTH_TOKEN_ERROR.getDesc());
        }

        long userId = JwtTokenUtil.getUserIdFromToken(token);

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, userId);

        return userId;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
