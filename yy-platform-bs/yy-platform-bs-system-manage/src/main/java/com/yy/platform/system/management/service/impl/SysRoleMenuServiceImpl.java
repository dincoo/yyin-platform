package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.system.management.dao.SysRoleMenuDao;
import com.yy.platform.system.management.entity.SysRoleMenu;
import com.yy.platform.system.management.service.SysRoleMenuService;
import com.yy.platform.system.management.utils.ToolForID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色与菜单对应关系
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	//@CacheEvict(value = {"roles","menus"},allEntries=true)
	public void saveOrUpdate(String roleId, List<String> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new String[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		List<SysRoleMenu> list = new ArrayList<>(menuIdList.size());
		for(String menuId : menuIdList){
			SysRoleMenu sysRoleMenuEntity = new SysRoleMenu();
			sysRoleMenuEntity.setId(ToolForID.getSysRoleMenuID());
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);

			list.add(sysRoleMenuEntity);
		}
		this.saveBatch(list);
	}

	@Override
    //@Cacheable(value = "roles",key = "#roleId + 'saveOrUpdate'")
	public List<String> queryMenuIdList(String roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public List<String> queryMenuIdListByRoleIds(List<String> roleIds) {
		return baseMapper.queryMenuIdListByRoleIds(roleIds);
	}

	@Override
	public int deleteBatch(String[] roleIds){
		return baseMapper.delete(new QueryWrapper<SysRoleMenu>().in("ROLE_ID",roleIds));
	}

}
