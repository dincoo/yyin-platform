package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysRoleMenu;

import java.util.List;


/**
 * 角色与菜单对应关系
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:42:30
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
	
	void saveOrUpdate(String roleId, List<String> menuIdList);

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<String> queryMenuIdList(String roleId);

	/**
	 * 根据角色id列表，获取菜单Id列表
	 * @param roleIds
	 * @return
	 */
	List<String> queryMenuIdListByRoleIds(List<String> roleIds);
	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(String[] roleIds);
	
}
