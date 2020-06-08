package com.yy.platform.component.starter.web.shiro.filter;

import com.yy.platform.component.starter.web.shiro.TokenSubjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Slf4j
public class PermissionFilter extends AuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println(httpServletRequest.getServletPath());
        String token = httpServletRequest.getParameter("token");
        Subject subject = TokenSubjectUtil.getSubject(token);
        if (subject == null) {
//          response.setContentType("application/json;charset=utf-8");
//          response.getWriter().write("{\"code\":50005,\"message\":\"未登录\"}");
            // subject = this.getSubject(request, response);
            return false;
        }
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        // throw ExceptionUtil.NOLOGIN;
        // return false;
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":50003,\"message\":\"未授权!\"}");
        log.warn("into PermissionFilter onAccessDenied ");
        return false;
    }
}
