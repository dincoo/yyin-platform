package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.yy.platform.component.starter.exception.BaseCustomizeException;
import com.yy.platform.system.management.dao.SysUserRoleDao;
import com.yy.platform.system.management.entity.SysUserRole;
import com.yy.platform.system.management.service.SysUserRoleService;
import com.yy.platform.system.management.utils.ToolForID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

	@Override
    //@CacheEvict(value = {"menus"},allEntries=true)
	public void saveOrUpdate(String userId, List<String> roleIdList) {
		//先删除用户与角色关系
		Map<String, Object> param = CollectionUtil.newHashMap();
		param.put("USER_ID", userId);
		this.removeByMap(param);

		if(roleIdList == null || roleIdList.size() == 0){
			throw new BaseCustomizeException("400","必须选择一个角色","");
		}
		
		//保存用户与角色关系
		List<SysUserRole> list = new ArrayList<>(roleIdList.size());
		for(String roleId : roleIdList){
			SysUserRole sysUserRoleEntity = new SysUserRole();
			sysUserRoleEntity.setId(ToolForID.getSysUserRoleID());
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		this.saveBatch(list);
	}

	@Override
	public List<String> queryRoleIdList(String userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(String[] roleIds){
		return baseMapper.delete(new QueryWrapper<SysUserRole>().in("ROLE_ID",roleIds));
	}
}
