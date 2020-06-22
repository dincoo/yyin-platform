package com.yy.platform.system.management.controller;

import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.service.SysApiPermService;
import com.yy.platform.system.management.service.SysMenuApiPermService;
import com.yy.platform.system.management.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 接口权限管理
 * 
 */
@Api(description="接口权限管理")
@RestController
@RequestMapping("/sys/api")
public class SysApiPermController {
	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysApiPermService sysApiPermService;

	@Autowired
	private SysMenuApiPermService sysMenuApiPermService;


	@RequiresPermissions("sys:api:list")
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("backstage/pages/sysManage/menu");
	}
	
	/**
	 * 所有api接口列表
	 */
	@ApiOperation(value = "获取接口权限列表")
	@GetMapping("/list")
	@RequiresPermissions("sys:api:list")
	public R list(){
		List<SysApiPerm> apiPermList = sysApiPermService.list(null);
		return R.Builder.success(apiPermList).build();
	}

	/*@GetMapping("/list1")
	public R list1(@LoginUser LoginUserInfo userInfo){
		System.out.println("-------:"+userInfo.toString());
		System.out.println("-------:"+userInfo.getName());
		List<SysApiPerm> apiPermList = sysApiPermService.list(null);
		return R.Builder.success(apiPermList).build();
	}*/

	/**
	 * 通过菜单id获取接口权限列表
	 */
	@ApiOperation(value = "获取接口权限信息",notes = "通过菜单id")
	@ApiImplicitParam(name = "menuId", value = "菜单id", paramType = "query", required = true, dataType = "String")
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:api:info")
	public R info(@PathVariable("menuId") String menuId){
		List<String> apiPermIds = sysMenuApiPermService.queryApiPermIds(menuId);
		List<SysApiPerm> apiPermList = new ArrayList<>();
		if(apiPermIds.size() > 0){
			apiPermList = sysApiPermService.listByIds(apiPermIds);
		}
		return R.Builder.success(apiPermList).build();
	}
	
	/**
	 * 保存接口权限
	 */
	//@SysLogAp("保存菜单")
	@ApiOperation(value = "保存接口权限")
	@PostMapping("/save")
	@RequiresPermissions("sys:api:save")
	public R save(@LoginUser LoginUserInfo userInfo, @RequestBody SysApiPerm sysApiPerm){
		if(StringUtils.isBlank(sysApiPerm.getMenuId())){
			return R.Builder.badReq().message("参数缺少菜单id").build();
		}
		//数据校验
		//verifyForm(menu);

		sysApiPerm.setCreateBy(userInfo.getId());
		if(sysApiPermService.save(sysApiPerm)){
			return R.Builder.success().message("保存接口权限成功").build();
		}
		return R.Builder.failure().message("保存接口权限失败").build();

	}
	
	/**
	 * 修改接口权限
	 */
	//@SysLogAp("修改菜单")
	@ApiOperation(value = "修改接口权限")
	@PutMapping("/update")
	@RequiresPermissions("sys:api:update")
	public R update(@LoginUser LoginUserInfo userInfo, @RequestBody SysApiPerm sysApiPerm){
		//数据校验
		//verifyForm(menu);

		sysApiPerm.setUpdateBy(userInfo.getId());
		if(sysApiPermService.updateById(sysApiPerm)){
			return R.Builder.success("修改接口权限成功").build();
		}
		return R.Builder.failure().message("修改接口权限失败").build();
	}
	
	/**
	 * 删除接口权限
	 */
	//@SysLogAp("删除菜单")
	@ApiOperation(value = "删除接口权限")
	@DeleteMapping("/delete")
	@RequiresPermissions("sys:api:delete")
	public R delete(@RequestBody String[] ids){

		sysApiPermService.deleteBatch(ids);

		return R.Builder.success("删除菜单成功").build();
	}

}