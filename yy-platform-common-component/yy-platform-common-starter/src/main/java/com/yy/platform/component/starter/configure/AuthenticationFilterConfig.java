package com.yy.platform.component.starter.configure;

import com.yy.platform.component.starter.web.auth.filter.AuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zhongdh
 * @Date: 2020/1/2 16:09
 * @Description: 认证过滤器配置
 */
@Slf4j
@Configuration
public class AuthenticationFilterConfig {

    @Value("${yy.platform.auth.exclusions:/druid,/swagger*,/webjars}")
    private String exclusions;

    /**
     * 通过FilterRegistrationBean类，自定义过滤器
     * @param authenticationFilter
     * @return
     */
    @Bean
    @ConditionalOnExpression("${yy.platform.auth.enabled:false}")
    public FilterRegistrationBean jwtAuthFilterRegistrationBean(AuthenticationFilter authenticationFilter) {
        log.debug("init jwtAuthFilterRegistrationBean");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(authenticationFilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.addInitParameter("exclusions",exclusions);
//        registrationBean.setOrder(1);//order的数值越小 则优先级越高
        return registrationBean;
    }

}
