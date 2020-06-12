package com.yy.platform.system.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.platform.system.management.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseMapper<SysUser> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	/*List<String> queryAllPerms(String userId);
	
	*//**
	 * 查询用户的所有菜单ID
	 *//*
	List<String> queryAllMenuId(String userId);
	
	List<ISysUser> selectUserList(@Param("roleIds") List<String> roleIds);*/

}
