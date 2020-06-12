package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.component.starter.result.pager.PageResult;
import com.yy.platform.system.management.entity.SysUser;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUser> {

	List<String> queryRoles(String userId);

    /**
     * 查询用户的接口权限
     * @param userId  用户ID
     */
	List<String> queryApiPerms(String userId);

	/**
	 * 查询用户的数据权限
	 * @param userId
	 * @return
	 */
	List<String> queryDataPerms(String userId);

	/**
	 * 查询用户列表
	 * @param params
	 * @return
	 */
	PageResult<SysUser> queryPage(Map<String, Object> params);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<String> queryAllMenuId(String userId);
	
	/**
	 * 保存用户
	 * @return 
	 */
	boolean save(SysUser user);
	
	/**
	 * 修改用户
	 */
	void update(SysUser user);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(String userId, String password, String newPassword);

    /**
     * 重置密码
     * @param idList        用户ID集合
     * @return
     */
    boolean resetBatchIds(List<String> idList);
    
    //List<ISysUser> getUserListWithAccount();
}
