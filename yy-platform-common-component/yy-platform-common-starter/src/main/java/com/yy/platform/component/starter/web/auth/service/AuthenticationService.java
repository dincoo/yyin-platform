package com.yy.platform.component.starter.web.auth.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yy.platform.component.starter.constants.ErrorCodeEnum;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.exception.BaseCustomizeException;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.auth.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zhongdh
 * @Date: 2020/1/2 10:33
 * @Description: 用户token检验器
 *
 */
@Slf4j
//@Service
public class AuthenticationService {


    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    public static final String LOGIN_TOKEN_KEY = "X-JLXshop-Token";
    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    public void authenticate(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        log.info("Request URL: %s", requestURI);
        String authToken = getTokenFromHeader(request);
        if(StringUtils.isBlank(authToken)){
            throw new BaseCustomizeException(ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getCode(),
                    ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getDesc(),
                    ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getDesc());
        }else{
            checkToken(authToken);
            putTokenAndUserInContext(authToken);
        }
    }

    /**
     * 进行token的校验
     * @param token
     */
    private void checkToken(String token){
        try {
            jwtTokenUtil.verifyToken(token);
        } catch (TokenExpiredException e) {
            throw new AuthException("token已过期", "checkToken");
        } catch (Exception e){
            throw new AuthException("非法请求", "checkToken");
        }
    }

    /**
     * 将token添加到当前线程上下文
     * @param authToken
     * @return
     */
    private boolean putTokenAndUserInContext(String authToken) {
//        Long userId = JwtTokenUtil.getUserIdFromToken(authToken);
//        User user = new User();
//        user.setUserId(userId);
//        SecurityContext.setToken(authToken);
//        SecurityContext.setUserPrincipal(user);
        return true;
    }

    /**
     * 从请求头中获取token
     * @param request
     * @return
     */
    public String getTokenFromHeader(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //从header中获取token
        String token = httpRequest.getHeader(LOGIN_TOKEN_KEY);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(LOGIN_TOKEN_KEY);
        }

        return token;
    }



}
