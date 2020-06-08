package com.yy.platform.bs.test.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.platform.bs.test.entity.GoodsEntity;
import com.yy.platform.bs.test.dao.IGoodsMapper;
import com.yy.platform.component.starter.base.BaseService;
import com.yy.platform.component.starter.orm.util.Query;
import com.yy.platform.component.starter.result.pager.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dincoo
 * @date 2020-03-27 10:06:57
 */
@Slf4j
@Service

public class GoodsServiceImpl extends BaseService<IGoodsMapper, GoodsEntity> implements IGoodsService {



    @Override
    public PageResult<GoodsEntity> findBaseGoodsPage(Query query, GoodsEntity entity) {

        Page<GoodsEntity> page = baseMapper.selectPage(query.getPage(), new QueryWrapper<>(entity));

        return PageResult.<GoodsEntity>builder()
                .data(page.getRecords())
                .count(page.getTotal())
                .current(page.getCurrent())
                .size(page.getSize())
                .build();
    }

}
