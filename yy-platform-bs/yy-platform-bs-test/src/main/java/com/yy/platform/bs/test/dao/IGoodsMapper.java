package com.yy.platform.bs.test.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.platform.bs.test.entity.GoodsEntity;
import com.yy.platform.component.starter.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dincoo
 * @date 2020-03-26 20:51:09
 */
public interface IGoodsMapper extends BaseMapper<GoodsEntity> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<GoodsEntity> findList(Page<GoodsEntity> page, @Param("p") Map<String, Object> params);
}
