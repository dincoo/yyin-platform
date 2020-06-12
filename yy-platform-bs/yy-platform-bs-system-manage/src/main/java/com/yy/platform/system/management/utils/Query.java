package com.yy.platform.system.management.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.LinkedHashMap;
import java.util.Map;

import com.yy.platform.component.starter.orm.util.SQLFilter;
import org.apache.commons.lang3.StringUtils;

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
    private int currPage = 1;
    //每页条数
    private int limit = 10;

    public Query(Map<String, Object> params){
        this.putAll(params);
        //分页参数
        if(params.get("current") != null){
            Object obj = params.get("current");
            if (obj instanceof Integer){
                currPage = (Integer)obj;
            }else{
                currPage = Integer.parseInt((String)obj);
            }
        }
        if(params.get("size") != null){
            Object obj = params.get("size");
            if (obj instanceof Integer){
                limit = (Integer)obj;
            }else{
                limit = Integer.parseInt((String)obj);
            }
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String)params.get("sidx"));
        String order = SQLFilter.sqlInject((String)params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
           // this.page.setOrderByField(sidx);
           // this.page.setAsc("ASC".equalsIgnoreCase(order));
        }

    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getLimit() {
        return limit;
    }
}
