package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author jaden
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptService extends IService<SysDept> {

	List<SysDept> queryList(Map<String, Object> map);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<String> queryDetpIdList(String parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<String> getSubDeptIdList(String deptId);

}
