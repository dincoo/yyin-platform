package com.yy.platform.component.starter.constants;

/**
 * 全局常量
 */
public interface CommonConstant {

    /**
     * 分页插件
     */
    static String PAGE_PAGE = "page";
    static String PAGE_SIZE = "limit";


    /**
     * 测试的userid
     */
    static Long TEST_USER_ID = 1L;



    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "yy-traceId-header";

    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";


    /**
     * 表字段 enable 默认值 有效
     */
    int TABLE_DEFAULT_VALUE_ENABLE = 1;
    /**
     * 表字段 enable 默认值 无效
     */
    int TABLE_DEFAULT_VALUE_UNENABLE = 0;

}
