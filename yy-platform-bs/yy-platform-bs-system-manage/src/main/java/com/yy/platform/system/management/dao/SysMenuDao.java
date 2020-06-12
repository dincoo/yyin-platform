package com.yy.platform.system.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.platform.system.management.entity.SysMenu;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:01
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenu> queryListParentId(String parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenu> queryNotButtonList();

	//Integer queryMaxId();

	List<String> queryAllId();

	/**
	 * 查询权限列表
	 * @param ids
	 * @return
	 */
	List<String> queryPermsByIds(List<String> ids);

}
