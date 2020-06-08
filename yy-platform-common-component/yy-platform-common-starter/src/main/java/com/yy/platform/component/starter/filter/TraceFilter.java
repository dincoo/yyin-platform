package com.yy.platform.component.starter.filter;

import cn.hutool.core.util.StrUtil;
import com.yy.platform.component.starter.configure.TraceConfig;
import com.yy.platform.component.starter.constants.CommonConstant;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 日志链路追踪过滤器
 * @author zhongdh
 */
@ConditionalOnClass(Filter.class)
public class TraceFilter extends OncePerRequestFilter {

    @Autowired
    private TraceConfig traceConfig;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !traceConfig.getEnable();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try{
            String traceId = httpServletRequest.getHeader(CommonConstant.TRACE_ID_HEADER);
            if(StrUtil.isNotEmpty(traceId)){
                MDC.put(CommonConstant.LOG_TRACE_ID, traceId);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }finally {
            MDC.clear();
        }

    }
}
