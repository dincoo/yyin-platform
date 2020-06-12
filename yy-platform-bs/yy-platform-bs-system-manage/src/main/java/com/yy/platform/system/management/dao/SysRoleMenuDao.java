package com.yy.platform.system.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.platform.system.management.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:33:46
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<String> queryMenuIdList(String roleId);

	List<String> queryMenuIdListByRoleIds(List<String> roleIds);
}
