package com.yy.platform.component.starter.web.shiro.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yy.platform.component.starter.constants.ErrorCodeEnum;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.exception.BaseCustomizeException;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.annotation.IgnoreAuth;
import com.yy.platform.component.starter.web.shiro.HmacToken;
import com.yy.platform.component.starter.web.shiro.TokenSubjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.security.Timestamp;

/**基于HMAC（ 散列消息认证码）的无状态认证过滤器
 *
 */
@Slf4j
public class HmacTokenFilter extends AccessControlFilter {

    @Autowired
    private TokenSubjectUtil tokenSubjectUtil;

    public static final String LOGIN_TOKEN_KEY = "token";
    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    public static final String DEFAULT_CLIENTKEY_PARAM = "clientKey";
    public static final String DEFAULT_TIMESTAMP_PARAM = "timeStamp";
    public static final String DEFAUL_DIGEST_PARAM = "digest";

    private final static JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    /**
     * 是否放行
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws Exception {






        IgnoreAuth annotation = null;
        if (mappedValue instanceof HandlerMethod) {
            annotation = ((HandlerMethod) mappedValue).getMethodAnnotation(IgnoreAuth.class);
        }
        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        if (null != getSubject(request, response)
                && getSubject(request, response).isAuthenticated()) {
            return true;//已经认证过直接放行
        }
        return false;//转到拒绝访问处理逻辑
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        //TODO refresh token
    }

    /**
     * 拒绝处理
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        String token = getTokenFromHeader(request);

        if(StringUtils.isBlank(token)) {
            throw new BaseCustomizeException(ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getCode(),
                    ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getDesc(),
                    ErrorCodeEnum.AUTH_TOKEN_EMPTY_FAIL.getDesc());
        }
        // 验证token
        checkToken(token);
        String clientKey = jwtTokenUtil.createTokenByUserId(JwtTokenUtil.getUserIdFromToken(token));
        AuthenticationToken authToken = new HmacToken(clientKey, "2020-06-06 10:20:21", "digest", "host",null);
        getSubject(request,response).login(authToken);
        //ThreadContext.bind(tokenSubjectUtil.getSubject(token));
        return true;
    }


    /**
     * 进行token的校验
     * @param token
     */
    //TODO 判断存在问题
    private void checkToken(String token){
        try {
            if(!jwtTokenUtil.verifyToken(token)){
                throw new AuthException("非法请求", "checkToken");
            }
        } catch (TokenExpiredException e) {
            throw new AuthException("token已过期", "checkToken");
        } catch (Exception e){
            throw new AuthException("非法请求", "checkToken");
        }
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
