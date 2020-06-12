package com.yy.platform.system.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.platform.component.starter.orm.util.Query;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.result.pager.PageResult;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.system.management.entity.SysUser;
import com.yy.platform.system.management.service.SysUserRoleService;
import com.yy.platform.system.management.service.SysUserService;
import com.yy.platform.system.management.utils.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author jaden
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    //@Autowired
    //private SysDeptService sysDeptService;

	/*@RequestMapping("/test")
	public String index(){
		return "test111";
	}
*/


    //@RequiresPermissions("sys:user:list")
    /*@RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("sysManage/admin");
    }*/

	/**
	 * 所有用户列表
	 */
	@ApiOperation(value = "获取用户分页列表")
	@PostMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R<PageResult<SysUser>> list(@RequestBody Map<String, Object> params){
		PageResult<SysUser> page = sysUserService.queryPage(params);
		return R.Builder.success(page).build();
	}

	/*@PostMapping("/validateUserName")
    public Object validateUserName(@RequestBody Map<String, Object> params){
        QueryWrapper<ISysUser> sysUserEntityWrapper = new QueryWrapper<>();
        sysUserEntityWrapper.eq("username",params.get("username"));
        int i = sysUserService.count(sysUserEntityWrapper);
        if (i > 0){
            return setSuccessModelMap(false);
        }
        return setSuccessModelMap(true);
    }
	
	@PostMapping("/validateMobile")
    public Object validateMobile(@RequestBody Map<String, Object> params){
        QueryWrapper<ISysUser> sysUserEntityWrapper = new QueryWrapper<>();
        sysUserEntityWrapper.eq("MOBILE",params.get("mobile"));
        int i = sysUserService.count(sysUserEntityWrapper);
        if (i > 0){
            return setSuccessModelMap(false);
        }
        return setSuccessModelMap(true);
    }*/

	/**
	 * 获取登录的用户信息
	 */
	/*@GetMapping("/info")
	public Object info(){
		return setSuccessModelMap(ShiroUtils.getUserEntity());
	}*/

	/**
	 * 修改登录用户密码
	 */
	//@SysLogAp("修改密码")
	@ApiOperation(value = "修改登录用户密码")
	@PutMapping("/password")
	public R password(@LoginUser LoginUserInfo userInfo, @RequestBody Map<String, Object> params){
		String newPassword = String.valueOf(params.get("newPassword"));
		String password = String.valueOf(params.get("password"));
		//原密码
		password = ShiroUtils.sha256(password, "SALT");
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, "SALT");

		//更新密码
		String userId = userInfo.getId();
		boolean flag = sysUserService.updatePassword(userId, password, newPassword);
		if(!flag){
			return R.Builder.badReq().message("原密码不正确").build();
		}

		return R.Builder.success().message("密码更新成功").build();
	}

	/**
	 * 用户信息
	 */
	@ApiOperation(value = "查询用户信息",notes = "通过用户id")
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") String userId){
		SysUser user = sysUserService.getById(userId);

		//获取用户所属的角色列表
		List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return R.Builder.success(user).build();
	}

	/**
	 * 保存用户
	 */
	//@SysLogAp("保存用户")
	@ApiOperation(value = "保存用户信息")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@LoginUser LoginUserInfo userInfo, @RequestBody SysUser user){
		//TODO 进行校验
		//ValidatorUtils.validateEntity(user);
        int existUser = sysUserService.count(new QueryWrapper<SysUser>().eq("username",user.getUsername()));
        if (existUser > 0){
            return R.Builder.badReq().message("已存在该用户名！").build();
        }
        user.setCreateBy(userInfo.getId());

		if(sysUserService.save(user)){
			return R.Builder.success().build();
		}
		return R.Builder.failure().message("保存用户失败").build();
	}

	/**
	 * 修改用户
	 */
	//@SysLogAp("修改用户")
	@ApiOperation(value = "修改用户信息")
	@PutMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@LoginUser LoginUserInfo userInfo, @RequestBody SysUser user){
        user.setUpdateBy(userInfo.getId());
		sysUserService.update(user);
		return R.Builder.success().build();
	}

	/**
	 * 删除用户
	 */
	//@SysLogAp("删除用户")
	@ApiOperation(value = "删除用户信息")
	@DeleteMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@LoginUser LoginUserInfo userInfo, @RequestBody String[] userIds){
		if(ArrayUtils.contains(userIds, "1")){
			return R.Builder.badReq().message("系统管理员不能删除").build();
		}

		if(ArrayUtils.contains(userIds, userInfo.getId())){
			return R.Builder.badReq().message("当前用户不能删除").build();
		}

		if(sysUserService.removeByIds(Arrays.asList(userIds))){
			return R.Builder.success().build();
		}
		//注：其他表都是由用户id进行关联的，没有删除其他表数据的操作，但暂时不影响使用。
		return R.Builder.failure().message("删除用户失败").build();
	}

	/**//**
     * 批量重置密码
     *//*
    //@SysLogAp("批量重置密码")
    @PutMapping("/resetPwd")
    //@RequiresPermissions("sys:user:resetPwd")
    public Object resetPwd(@RequestBody String[] userIds){
        if(ArrayUtils.contains(userIds, "1")){
            return setModelMap(new ModelMap(), HttpCode.BAD_REQUEST, "系统管理员不能重置密码！");
        }
		sysUserService.resetBatchIds(Arrays.asList(userIds));
        return setSuccessModelMap(userIds.length);
    }
    @GetMapping("/account")
    public Object userList() {
        logger.info("获取系统账号");
        return setSuccessModelMap(sysUserService.getUserListWithAccount());
    }
    */

}
