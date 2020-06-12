package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysMenu;

import java.util.List;


/**
 * 菜单管理
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:42:16
 */
public interface SysMenuService extends IService<SysMenu> {

	/**
	 * 查询菜单下的接口权限列表
	 * @return
	 */
	List<SysMenu> getMenuApi();

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<SysMenu> queryListParentId(String parentId, List<String> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenu> queryListParentId(String parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenu> queryNotButtonList();

	/**
	 * 获取用户菜单列表
	 */
	List<SysMenu> getUserMenuList(String userId);

    /**
     * 查询菜单最大Id
     * @return
			 */
    Integer queryMaxId();

	/**
	 * 删除
	 */
	void delete(String menuId);

	/**
	 * 查询权限列表
	 * @param ids
	 * @return
	 */
	List<String> queryPermsByIds(List<String> ids);

}
