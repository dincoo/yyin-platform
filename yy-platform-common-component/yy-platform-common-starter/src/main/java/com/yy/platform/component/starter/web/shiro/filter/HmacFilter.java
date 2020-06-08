package com.yy.platform.component.starter.web.shiro.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yy.platform.component.starter.constants.ErrorCodeEnum;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.exception.BaseCustomizeException;
import com.yy.platform.component.starter.util.JwtTokenUtil;
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

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**基于HMAC（ 散列消息认证码）的无状态认证过滤器
 *
 */
@Slf4j
public class HmacFilter extends AccessControlFilter {


    public static final String LOGIN_TOKEN_KEY = "X-JLXshop-Token";
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
        if (null != getSubject(request, response)
                && getSubject(request, response).isAuthenticated()) {
            return true;//已经认证过直接放行
        }
        return false;//转到拒绝访问处理逻辑
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

        ThreadContext.bind(TokenSubjectUtil.getSubject(token));

        return true;//打住，访问到此为止
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
