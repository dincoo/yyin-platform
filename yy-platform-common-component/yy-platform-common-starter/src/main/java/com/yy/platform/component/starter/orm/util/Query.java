package com.yy.platform.component.starter.orm.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author jaden
 * @date 2017-03-14 23:15
 */
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    //mybatis-plus分页参数
    private Page<T> page;
    //当前页码
    private Long currPage = 1L;
    //每页条数
    private Long limit = 10L;

    public static final String CURRENT = "current";
    public static final String SIZE = "size";

    public Query(Long current, Long size){
        this();
        Map<String,Object> params = new HashMap<>();
        params.put(CURRENT, current);
        params.put(SIZE, size);
        this.initQuery(params);
    }

    public Query(){}

    public Query(Map<String, Object> params){
        this();
        this.initQuery(params);
    }

    private void initQuery(Map<String, Object> params){
        this.putAll(params);
        //分页参数
        currPage = MapUtils.getLongValue(params, CURRENT);
        limit = MapUtils.getLongValue(params, SIZE);

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String order = null;
        String sidx = null;
        Object sidxObj = params.get("sidx");
        if(null != sidxObj){
            sidx = SQLFilter.sqlInject((String)sidxObj);
            this.put("sidx", sidx);
        }
        Object orderObj = params.get("order");
        if(null != orderObj){
            order = SQLFilter.sqlInject((String)orderObj);
            this.put("order", order);
        }

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            // this.page.
            if("ASC".equalsIgnoreCase(order)){
                this.page.setAsc(sidx);
            }else if("DESC".equalsIgnoreCase(order)){
                this.page.setDesc(sidx);
            }
        }
    }

    public Page<T> getPage() {
        return page;
    }

    public Long getCurrPage() {
        return currPage;
    }

    public Long getLimit() {
        return limit;
    }
}
