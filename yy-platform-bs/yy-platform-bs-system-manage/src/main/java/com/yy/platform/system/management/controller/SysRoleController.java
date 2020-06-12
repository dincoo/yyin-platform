package com.yy.platform.system.management.controller;

import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.result.pager.PageResult;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.system.management.entity.SysRole;
import com.yy.platform.system.management.entity.SysRoleApiPerm;
import com.yy.platform.system.management.entity.SysUser;
import com.yy.platform.system.management.service.SysApiPermService;
import com.yy.platform.system.management.service.SysRoleApiPermService;
import com.yy.platform.system.management.service.SysRoleMenuService;
import com.yy.platform.system.management.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 角色管理
 * 
 * @author jaden
 * 
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Autowired
	private SysRoleApiPermService sysRoleApiPermService;
	/*@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysDeptService sysDeptService;*/

	@RequiresPermissions("sys:role:list")
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("sysManage/role");
	}

	/**
	 * 获取角色分页列表
	 */
	@ApiOperation(value = "获取角色分页列表")
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	@CacheEvict(value="users",allEntries=true)
	public R list(@RequestBody Map<String, Object> params){

		PageResult<SysUser> page = sysRoleService.queryPage(params);

		return R.Builder.success(page).build();
	}
	
	/**
	 * 获取角色列表
	 */
	@ApiOperation(value = "获取角色列表")
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		List<SysRole> list = sysRoleService.list(null);
		return R.Builder.success(list).build();
	}
	
	/**
	 * 获取角色信息
	 */
	@ApiOperation(value = "获取角色信息",notes = "通过角色id")
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") String roleId){
		SysRole role = sysRoleService.getById(roleId);

		//查询角色对应的菜单
		List<String> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		return R.Builder.success(role).build();

		/*SysDept sysDeptEntity = sysDeptService.selectById(role.getDeptId());
		role.setDeptName(sysDeptEntity.getName());*/

		//查询角色对应的部门
		/*List<String> deptIdList = sysRoleDeptService.queryDeptIdList(new String[]{roleId});
		role.setDeptIdList(deptIdList);*/
	}
	
	/**
	 * 保存角色
	 */
	//@SysLogAp("保存角色")
	@ApiOperation(value = "保存角色信息")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@LoginUser LoginUserInfo userInfo, @RequestBody SysRole role){
		//TODO 角色数据校验
		//ValidatorUtils.validateEntity(role);

		role.setCreateBy(userInfo.getId());
		if(sysRoleService.save(role)){
			return R.Builder.success().message("保存成功").build();
		}
		return R.Builder.failure().message("保存角色失败").build();
	}
	
	/**
	 * 修改角色
	 */
	//@SysLogAp("修改角色")
	@ApiOperation(value = "修改角色信息")
	@PutMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@LoginUser LoginUserInfo userInfo, @RequestBody SysRole role){
		//TODO 角色数据校验
//		ValidatorUtils.validateEntity(role);

		role.setUpdateBy(userInfo.getId());
		sysRoleService.update(role);
		
		return R.Builder.success().message("更新成功").build();
	}
	
	/**
	 * 删除角色
	 */
	//@SysLogAp("删除角色")
	@ApiOperation(value = "删除角色信息")
	@DeleteMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody String[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.Builder.success().message("删除成功").build();
	}

	/**
	 * 角色获取权限列表
	 */
	//@SysLogAp("删除角色")
	@ApiOperation(value = "获取权限列表",notes = "通过角色id")
	@GetMapping("/perm/{id}")
	@RequiresPermissions("sys:role:perm")
	public R getPerm(@PathVariable("id") String id){
		List<String> perms = sysRoleApiPermService.queryPermIds(Arrays.asList(id));
		return R.Builder.success(perms).build();
	}
}
