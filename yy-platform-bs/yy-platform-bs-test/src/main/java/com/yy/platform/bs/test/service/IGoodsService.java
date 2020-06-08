package com.yy.platform.bs.test.service;


import com.yy.platform.bs.test.entity.GoodsEntity;
import com.yy.platform.component.starter.base.IBaseService;
import com.yy.platform.component.starter.orm.util.Query;
import com.yy.platform.component.starter.result.pager.PageResult;

/**
 * 
 *
 * @author dincoo
 * @date 2020-03-27 10:06:57
 */
public interface IGoodsService extends IBaseService<GoodsEntity> {

    /**
     * 查询刷选商品基本信息
     * @param query
     * @param entity
     * @return
     */
    PageResult<GoodsEntity> findBaseGoodsPage(Query query, GoodsEntity entity);


}

