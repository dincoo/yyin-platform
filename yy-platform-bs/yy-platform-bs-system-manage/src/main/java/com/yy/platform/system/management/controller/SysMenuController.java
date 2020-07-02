package com.yy.platform.system.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.platform.component.starter.exception.ApiException;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.system.management.contants.Constant;
import com.yy.platform.system.management.entity.SysMenu;
import com.yy.platform.system.management.service.SysMenuService;
import com.yy.platform.system.management.vo.AsidebarMenuVo;
import com.yy.platform.system.management.vo.ChildMenuVo;
import com.yy.platform.system.management.vo.UserMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 系统菜单
 * 
 * @author jaden
 * 
 */
@Api(description="菜单管理")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;


	/*@RequiresPermissions("sys:menu:list")
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("backstage/pages/sysManage/menu");
	}
*/
	/**
	 * 查询菜单，含接口权限列表
	 * @return
	 */
	@ApiOperation(value = "查询菜单，含接口权限列表")
	@GetMapping("/api")
	@RequiresPermissions("sys:menu:api")
	public R menulist(){
		List<SysMenu> menuList = sysMenuService.getMenuApi();
		return R.Builder.success(menuList).build();
	}

	/**
	 * 导航菜单
	 */
	@ApiOperation(value = "导航菜单")
	@GetMapping("/nav")
	public R nav(@LoginUser LoginUserInfo userInfo){
		List<SysMenu> menuList = sysMenuService.getUserMenuList(userInfo.getId());
		return R.Builder.success(menuList).build();
	}
	
	/**
	 * 所有菜单列表
	 */
	@ApiOperation(value = "所有菜单列表")
	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public R list(){
		List<SysMenu> menuList = sysMenuService.list(null);
		for(SysMenu sysMenuEntity : menuList){
			SysMenu parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}
		return R.Builder.success(menuList).build();
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@ApiOperation(value = "选择菜单(添加、修改菜单)")
	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public R select(){
		//查询列表数据
		List<SysMenu> menuList = sysMenuService.queryNotButtonList();
        boolean b = menuList.stream().anyMatch(sysMenu -> "0".equals(sysMenu.getId()));
        if (!b){
            //添加顶级菜单
            SysMenu root = new SysMenu();
            root.setId("0");
            root.setName("一级菜单");
            root.setParentId("-1");
            root.setType(0);
            menuList.add(root);
        }
		return R.Builder.success(menuList).build();
	}
	
	/**
	 * 菜单信息
	 */
	@ApiOperation(value = "获取菜单信息",notes = "通过菜单id")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:menu:info")
	public Object info(@PathVariable("id") String id){
		SysMenu menu = sysMenuService.getById(id);
		return R.Builder.success(menu).build();
	}
	
	/**
	 * 保存菜单
	 */
	//@SysLogAp("保存菜单")
	@ApiOperation(value = "保存菜单信息")
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public Object save(@LoginUser LoginUserInfo userInfo, @RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		menu.setCreateBy(userInfo.getId());
		menu.setCreateTime(new Date());
		Integer id = sysMenuService.queryMaxId();
		menu.setId(String.valueOf(id + 1));
		if(sysMenuService.save(menu)){
			return R.Builder.success("保存菜单成功").build();
		}
		return R.Builder.failure().message("保存菜单失败").build();
	}
	
	/**
	 * 修改菜单
	 */
	//@SysLogAp("修改菜单")
	@ApiOperation(value = "修改菜单信息")
	@PutMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public Object update(@LoginUser LoginUserInfo userInfo, @RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		menu.setUpdateBy(userInfo.getId());
		menu.setUpdateTime(new Date());
		if(sysMenuService.updateById(menu)){
			return R.Builder.success("修改菜单成功").build();
		}
		return R.Builder.failure().message("修改菜单失败").build();
	}
	
	/**
	 * 删除
	 */
	//@SysLogAp("删除菜单")
	@ApiOperation(value = "删除菜单信息")
	@DeleteMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public R delete(@RequestBody String[] ids){
	
		// 先支持菜单一次只能删除一个
		if(null == ids || ids.length == 0 || ids.length > 1)
			return R.Builder.badReq().message("一次只能删除一个菜单").build();
		
		String id = ids[0];
		/*if(Integer.valueOf(id) <= 30){
			return setModelMap(new ModelMap(), HttpCode.BAD_REQUEST, "系统菜单，不能删除");
		}*/

		//判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.queryListParentId(id);
		if(menuList.size() > 0){
			return R.Builder.badReq().message("请先删除子菜单或按钮").build();
		}

		sysMenuService.delete(id);

		return R.Builder.success("删除菜单成功").build();
	}
	
    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new ApiException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new ApiException("上级菜单不能为空");
        }

        // 菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUri())) {
                throw new ApiException("菜单URL不能为空");
            }
        }

        // 上级菜单类型
        int parentType = Constant.MenuType.MODULES.getValue();
        if (!"-1".equals(menu.getParentId())) {
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        // 目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue()
                || menu.getType() == Constant.MenuType.MODULES.getValue()) {
            if (parentType != Constant.MenuType.MODULES.getValue()) {
                throw new ApiException("上级菜单只能为模块类型");
            }
            return;
        }

        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new ApiException("上级菜单只能为目录类型");
            }
        }

        // 按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new ApiException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
	

    /**
     * 根据当前登录的用户id查询出其对应的菜单列表
     */
	@ApiOperation(value = "根据当前登录的用户id，查询菜单列表")
    @GetMapping("/listMenu")
    public Object listMenuByUserId(@LoginUser LoginUserInfo userInfo){
        List<AsidebarMenuVo> asidebarMenuVoList = new ArrayList<>();
        String userId = userInfo.getId();
        //目录与菜单
		List<SysMenu> userMenuList = sysMenuService.getUserMenuList2(userInfo.getMenus());
        //List<SysMenu> userMenuList = sysMenuService.getUserMenuList(userId);
        userMenuList.forEach(sysMenu -> {
            AsidebarMenuVo asidebarMenuVo = new AsidebarMenuVo();
            asidebarMenuVo.setId(sysMenu.getId());
            asidebarMenuVo.setName(sysMenu.getName());
            asidebarMenuVo.setParentId(sysMenu.getParentId());
            asidebarMenuVo.setUrl(sysMenu.getUri());
            asidebarMenuVo.setIndexNum(sysMenu.getOrderNum().toString());
            asidebarMenuVo.setIconClass(sysMenu.getIcon());
            asidebarMenuVoList.add(asidebarMenuVo);
            if (sysMenu.getSubSysMenus() == null){
                return;
            }
            sysMenu.getSubSysMenus().forEach(childSysMenu -> {
                ChildMenuVo childMenuVo = new ChildMenuVo();
                
                childMenuVo.setName(childSysMenu.getName());
                childMenuVo.setIndexNum(childSysMenu.getOrderNum().toString());
                String uri = childSysMenu.getUri();
                int lastIndex = uri.lastIndexOf("/");
                String path = uri.substring(lastIndex+1);
				childMenuVo.setParendId(sysMenu.getId());
                childMenuVo.setId(path);
                childMenuVo.setPath(uri);
                asidebarMenuVo.getChildren().add(childMenuVo);
            });

        });
		//模块部分
        List<AsidebarMenuVo> moduleMenuVoList = new ArrayList();
		List<SysMenu> sysModuleMenus = sysMenuService.list(new QueryWrapper<SysMenu>().eq("type",Constant.MenuType.MODULES.getValue()));
		sysModuleMenus.forEach(sysModuleMenu -> {
			AsidebarMenuVo asidebarMenuVo = new AsidebarMenuVo();
			asidebarMenuVo.setId(sysModuleMenu.getId());
			asidebarMenuVo.setName(sysModuleMenu.getName());
			asidebarMenuVo.setUrl(sysModuleMenu.getUri());
			asidebarMenuVo.setIconClass(sysModuleMenu.getIcon());
			asidebarMenuVo.setIndexNum(sysModuleMenu.getOrderNum().toString());
			moduleMenuVoList.add(asidebarMenuVo);
		});

		UserMenuVo userMenuVo = new UserMenuVo();
		userMenuVo.setAsider(asidebarMenuVoList);
		userMenuVo.setModule(moduleMenuVoList);
        return R.Builder.success(userMenuVo).build();
    }
}
