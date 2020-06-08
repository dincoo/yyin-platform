package com.yy.platform.component.starter.web.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        AuthenticationToken token = context.getAuthenticationToken();
        if((token instanceof HmacToken)){
            // 当token为HmacToken时， 不创建 session
            context.setSessionCreationEnabled(false);
        }
        return super.createSubject(context);
    }
}
