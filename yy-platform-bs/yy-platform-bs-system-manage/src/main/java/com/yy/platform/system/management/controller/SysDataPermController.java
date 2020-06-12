package com.yy.platform.system.management.controller;

import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.system.management.entity.SysDataPerm;
import com.yy.platform.system.management.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 数据权限
 * 
 */
@RestController
@RequestMapping("/sys/data")
public class SysDataPermController {
	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysDataPermService sysPermService;

	@Autowired
	private SysMenuDataPermService sysMenuPermService;

	@RequiresPermissions("sys:data:list")
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("backstage/pages/sysManage/menu");
	}
	
	/**
	 * 所有数据权限列表
	 */
	@ApiOperation(value = "查询所有数据权限列表")
	@GetMapping("/list")
	@RequiresPermissions("sys:data:list")
	public R list(){
		List<SysDataPerm> apiPermList = sysPermService.list(null);
		return R.Builder.success(apiPermList).build();
	}

	/**
	 * 通过菜单id获取数据权限列表
	 */
	@ApiOperation(value = "获取数据权限列表",notes = "通过菜单id")
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:data:info")
	public R info(@PathVariable("menuId") String menuId){
		List<String> permIds = sysMenuPermService.queryPermIds(menuId);
		List<SysDataPerm> permList = new ArrayList<>();
		if(permIds.size() > 0){
			permList = sysPermService.listByIds(permIds);
		}
		return R.Builder.success(permList).build();
	}
	
	/**
	 * 保存数据权限
	 */
	//@SysLogAp("保存菜单")
	@ApiOperation(value = "保存数据权限")
	@PostMapping("/save")
	@RequiresPermissions("sys:data:save")
	public R save(@LoginUser LoginUserInfo userInfo, @RequestBody SysDataPerm sysDataPerm){
		if(StringUtils.isBlank(sysDataPerm.getMenuId())){
			return R.Builder.badReq().message("参数缺少菜单id").build();
		}
		//数据校验
		//verifyForm(menu);

		sysDataPerm.setCreateBy(userInfo.getId());
		if(sysPermService.save(sysDataPerm)){
			return R.Builder.success().message("保存数据权限成功").build();
		}
		return R.Builder.failure().message("保存数据权限失败").build();

	}
	
	/**
	 * 修改数据权限
	 */
	//@SysLogAp("修改菜单")
	@ApiOperation(value = "修改数据权限")
	@PutMapping("/update")
	@RequiresPermissions("sys:data:update")
	public R update(@LoginUser LoginUserInfo userInfo,@RequestBody SysDataPerm sysDataPerm){
		//数据校验
		//verifyForm(menu);

		sysDataPerm.setUpdateBy(userInfo.getId());
		if(sysPermService.updateById(sysDataPerm)){
			return R.Builder.success("修改数据权限成功").build();
		}
		return R.Builder.failure().message("修改数据权限失败").build();
	}
	
	/**
	 * 删除数据权限
	 */
	//@SysLogAp("删除菜单")
	@ApiOperation(value = "删除数据权限")
	@DeleteMapping("/delete")
	@RequiresPermissions("sys:data:delete")
	public R delete(@RequestBody String[] ids){

		sysPermService.deleteBatch(ids);

		return R.Builder.success("删除接口权限成功").build();
	}

}
