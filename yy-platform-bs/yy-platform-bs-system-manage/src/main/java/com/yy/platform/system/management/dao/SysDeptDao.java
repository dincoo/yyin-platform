package com.yy.platform.system.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.platform.system.management.entity.SysDept;

import java.util.List;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptDao extends BaseMapper<SysDept> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<String> queryDetpIdList(String parentId);

}
