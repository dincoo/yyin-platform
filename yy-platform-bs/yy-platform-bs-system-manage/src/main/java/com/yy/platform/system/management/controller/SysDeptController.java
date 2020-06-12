package com.yy.platform.system.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.system.management.contants.Constant;
import com.yy.platform.system.management.entity.SysDept;
import com.yy.platform.system.management.service.SysDeptService;
import com.yy.platform.system.management.utils.ShiroUtils;
import com.yy.platform.system.management.utils.ToolForID;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 部门管理
 * 
 * @author jaden
 * 
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
	@Autowired
	private SysDeptService sysDeptService;
	/*@Autowired
    private SysUserService sysUserService;
	@Autowired
    private SysRoleService sysRoleService;*/

	@RequiresPermissions("sys:dept:list")
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("sysManage/department");
	}
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "查询部门列表")
	@GetMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public R list(){
		List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());
		return R.Builder.success(deptList).build();
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@ApiOperation(value = "选择部门列表")
	@GetMapping("/select")
	@RequiresPermissions("sys:dept:select")
	public R select(){
		List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());
		//TODO 获取操作的用户
		//ISysUser sysUser = ShiroUtils.getUserEntity();

		//超级管理员可操作，添加一级部门
		if(ShiroUtils.getUserId() == Constant.SUPER_ADMIN){
			SysDept root = new SysDept();
			root.setId("0");
			root.setName("一级部门");
			root.setParentId("-1");
			deptList.add(root);
		}

		return R.Builder.success(deptList).build();
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
/*	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public Object info(){
		long id = 0;
		// TODO 重写该逻辑
//		if(sysUser.getId() != Constant.SUPER_ADMIN){
//			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
//			String parentId = null;
//			for(SysDeptEntity sysDeptEntity : deptList){
//				if(parentId == null){
//					parentId = sysDeptEntity.getParentId();
//					continue;
//				}
//
//				if(parentId > sysDeptEntity.getParentId().longValue()){
//					parentId = sysDeptEntity.getParentId();
//				}
//			}
//			id = parentId;
//		}
		return setSuccessModelMap(id);
	}*/
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "查询部门信息",notes = "通过部门id")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:dept:info")
	public R info(@PathVariable("id") String id){
		SysDept dept = sysDeptService.getById(id);
		
		return R.Builder.success(dept).build();
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存部门信息")
	@PostMapping("/save")
	//@SysLogAp("保存部门")
	@RequiresPermissions("sys:dept:save")
	public R save(@LoginUser LoginUserInfo userInfo, @RequestBody SysDept dept){
		dept.setId(ToolForID.getSysDeptID());
		dept.setEnable(1);
		dept.setCreateTime(new Date());

		dept.setCreateBy(userInfo.getId());
		if(sysDeptService.save(dept)){
			return R.Builder.success().message("保存部门成功").build();
		}
		return R.Builder.failure().message("保存部门失败").build();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改部门信息")
	@PutMapping("/update")
	//@SysLogAp("修改部门")
	@RequiresPermissions("sys:dept:update")
	public R update(@LoginUser LoginUserInfo userInfo, @RequestBody SysDept dept){

	    dept.setUpdateBy(userInfo.getId());
	    dept.setUpdateTime(new Date());
		if(sysDeptService.updateById(dept)){
			return R.Builder.success().message("更新部门成功").build();
		}
		return R.Builder.failure().message("更新部门失败").build();
	}
	
	/**
	 * 删除
	 */
	/*@DeleteMapping("/delete/{id}")
	//@SysLogAp("删除部门")
	@RequiresPermissions("sys:dept:delete")
	public Object delete(@PathVariable String id){
		//判断是否有子部门
		List<String> deptList = sysDeptService.queryDetpIdList(id);
		//判断是否在管理员管理中被引用
        List<ISysUser> sysUsers = sysUserService.list(new QueryWrapper<ISysUser>()
                .eq("dept_id", id));
        //判断是否在角色管理中被引用
        List<SysRole> sysRoles = sysRoleService.list(new QueryWrapper<SysRole>()
                .eq("dept_id", id));
        if(!deptList.isEmpty() || !sysUsers.isEmpty() || !sysRoles.isEmpty()){
			return setSuccessModelMap(0);
		}

		sysDeptService.removeById(id);
		
		return setSuccessModelMap(1);
	}*/
	
}
