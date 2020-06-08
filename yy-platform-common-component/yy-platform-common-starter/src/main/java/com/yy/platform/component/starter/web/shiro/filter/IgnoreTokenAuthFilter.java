package com.yy.platform.component.starter.web.shiro.filter;

import com.yy.platform.component.starter.web.annotation.IgnoreAuth;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class IgnoreTokenAuthFilter extends HmacTokenFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //TODO 通过 @IgnoreAuth 注解 实现anon的效果

        return false;
    }
}
