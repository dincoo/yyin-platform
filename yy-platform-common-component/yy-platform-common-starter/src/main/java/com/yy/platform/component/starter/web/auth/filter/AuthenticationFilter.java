package com.yy.platform.component.starter.web.auth.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.result.ResultStatus;
import com.yy.platform.component.starter.result.model.ResultData;
import com.yy.platform.component.starter.web.auth.SecurityContext;
import com.yy.platform.component.starter.web.auth.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhongdh
 * @Date: 2020/1/2 10:06
 * @Description: 用户验证过滤器
 */
@Slf4j
@Component
@ConditionalOnExpression("${yy.platform.auth.enabled:false}")
public class AuthenticationFilter implements Filter {

    @Autowired
    private AuthenticationService authenticationService;

    private List<String> exclusions=new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.info("init exclusions");
        String exclusions =filterConfig.getInitParameter("exclusions");
        if(StringUtils.isNotBlank(exclusions)){
            this.exclusions.addAll(Lists.newArrayList(exclusions.split(","))) ;
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try{
            // 清空安全上下文内容
            SecurityContext.cleanContext();
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            String requestUrl = request.getRequestURI();
            if(!checkExclusions(requestUrl)){
                // 进行token的验证以及初始化安全信息上下文的初始化
                authenticationService.authenticate(servletRequest, servletResponse);
            }

            // 执行具体业务
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (AuthException ae){
            log.error("token验证异常", ae);
            errorResponse(servletResponse, ResultStatus.UNAUTHORIZED, ae.getErrorMessage());
        }catch (Exception e){
            log.error("系统异常执行", e);
            errorResponse(servletResponse, ResultStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    private void errorResponse(ServletResponse servletResponse, ResultStatus status, String message){
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            response.setStatus(status.getHttpStatus().value());
            response.addHeader("Content-Type","application/json;charset=UTF-8");
            printWriter.write(JSON.toJSONString(ResultData.failure(status, message)));
        } catch (IOException e) {
            log.error("错误响应异常：", e);
        } finally {
            if(null != printWriter){
                printWriter.close();
            }
        }

    }

    /**
     * 检查该请求是否跳过进行token验证
     * @param requestUrl
     * @return
     */
    private boolean checkExclusions(String requestUrl){
        if(StringUtils.isNotBlank(requestUrl) && CollectionUtil.isNotEmpty(this.exclusions)){
            for(String exclusion : exclusions){
                if(-1 != requestUrl.indexOf(exclusion)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
