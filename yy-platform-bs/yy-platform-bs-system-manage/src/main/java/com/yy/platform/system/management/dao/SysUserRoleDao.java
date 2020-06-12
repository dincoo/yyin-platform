package com.yy.platform.system.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.platform.system.management.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;


/**
 * 用户与角色对应关系
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:34:46
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<String> queryRoleIdList(String userId);
}
