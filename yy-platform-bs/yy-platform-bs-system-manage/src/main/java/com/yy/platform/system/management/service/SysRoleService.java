package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.component.starter.result.pager.PageResult;
import com.yy.platform.system.management.entity.SysRole;
import com.yy.platform.system.management.entity.SysUser;

import java.util.Map;


/**
 * 角色
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRole> {

	PageResult<SysUser> queryPage(Map<String, Object> params);

	boolean save(SysRole role);

	void update(SysRole role);
	
	void deleteBatch(String[] roleIds);

}
