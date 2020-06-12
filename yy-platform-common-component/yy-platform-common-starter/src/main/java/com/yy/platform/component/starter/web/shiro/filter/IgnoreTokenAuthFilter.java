package com.yy.platform.component.starter.web.shiro.filter;

import com.yy.platform.component.starter.web.annotation.IgnoreAuth;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class IgnoreTokenAuthFilter extends HmacTokenFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 通过 @IgnoreAuth 注解 实现anon的效果
        IgnoreAuth annotation;
        if (mappedValue instanceof HandlerMethod) {
            annotation = ((HandlerMethod) mappedValue).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }
        return false;
    }
}
