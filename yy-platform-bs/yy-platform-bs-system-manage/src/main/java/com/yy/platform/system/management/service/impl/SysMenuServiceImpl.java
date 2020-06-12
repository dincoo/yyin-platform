package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.yy.platform.system.management.contants.Constant;
import com.yy.platform.system.management.dao.SysMenuDao;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysMenu;
import com.yy.platform.system.management.entity.SysMenuApiPerm;
import com.yy.platform.system.management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService{
	@Autowired
	@Lazy
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Autowired
	private SysMenuApiPermService sysMenuApiPermService;

	@Autowired
	private SysApiPermService sysApiPermService;

	/**
	 * 查询含接口权限列表的菜单
	 * @return
	 */
	@Override
	public List<SysMenu> getMenuApi(){
		List<SysMenu> sysMenus = baseMapper.selectList(null);
		for(SysMenu sysMenu : sysMenus){
			if(Constant.MenuType.BUTTON.getValue() == sysMenu.getType()){
				List<SysMenuApiPerm> sysMenuApiPerms = sysMenuApiPermService.list(new QueryWrapper<SysMenuApiPerm>().eq("MENU_ID",sysMenu.getId()));
				List<String> sysApiPermIds = sysMenuApiPerms.stream().map(x -> x.getApiPermId()).collect(Collectors.toList());
				if(sysApiPermIds.size() > 0){
					sysMenu.setSysApiPerms(sysApiPermService.list(new QueryWrapper<SysApiPerm>().in("ID_",sysApiPermIds)));
				}
			}
		}
		return sysMenus;
	}

    @Override
    //@CacheEvict(value="menus",allEntries=true)
    public boolean save(SysMenu entity) {
        return super.save(entity);
    }

    @Override
    //@CacheEvict(value="menus",allEntries=true)
    public boolean updateById(SysMenu entity) {
        return super.updateById(entity);
    }

    @Override
    //@Cacheable(value = "menus",key = "#id + 'selectById'")
	public SysMenu getById(Serializable id) {
		return super.getById(id);
	}

    @Override
    //@Cacheable(value = "menus",key = "#wrapper + 'selectList'")
    public List<SysMenu> list(Wrapper<SysMenu> wrapper) {
        return super.list(wrapper);
    }

    @Override
    //@Cacheable(value = "menus",key = "#parentId + 'queryListParentId'")
	public List<SysMenu> queryListParentId(String parentId, List<String> menuIdList) {
		List<SysMenu> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenu> userMenuList = new ArrayList<>();
		for(SysMenu menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
    //@Cacheable(value = "menus",key = "#parentId + 'queryListParentId'")
	public List<SysMenu> queryListParentId(String parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
    //@Cacheable(value = "menus",key = "'queryNotButtonList'")
	public List<SysMenu> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
    //@Cacheable(value = "menus",key = "#userId + 'getUserMenuList'")
	public List<SysMenu> getUserMenuList(String userId) {
		//系统管理员，拥有最高权限
		if(Constant.SUPER_ADMIN.equals(userId)){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<String> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public Integer queryMaxId() {
    	List<String> idList = baseMapper.queryAllId();
		List<Integer> ids = idList.stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		Integer id = Collections.max(ids);
		return id;
	}



	@Override
    //@CacheEvict(value="menus",allEntries=true)
	public void delete(String menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		Map<String, Object> param = CollectionUtil.newHashMap();
		param.put("menu_id", menuId);
		sysRoleMenuService.removeByMap(param);
	}

	/**
	 * 查询权限列表
	 * @param ids
	 * @return
	 */
	@Override
	public List<String> queryPermsByIds(List<String> ids) {
		return this.queryPermsByIds(ids);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<String> menuIdList){
		//查询根菜单列表
		//List<SysMenu> menuList = queryListParentId("0", menuIdList);
	    if(menuIdList!=null){
	        QueryWrapper<SysMenu> qw =new QueryWrapper<>();
	        qw.eq("type", "0");
	        qw.orderByAsc("order_num");
	        List<SysMenu> menuList = this.list(qw);
	        List<SysMenu>userMenuList = new ArrayList<>();
	        for(SysMenu menu : menuList){
	            if(menuIdList.contains(menu.getId())){
	                userMenuList.add(menu);
	            }
	        }
	        //递归获取子菜单
	        getMenuTreeList(userMenuList, menuIdList);
	        return userMenuList;
	    }else{
	        List<SysMenu> menuList = queryListParentId("0", menuIdList); 
	        getMenuTreeList(menuList, menuIdList);
	        return menuList;
	    }
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<String> menuIdList){
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		
		for(SysMenu entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setSubSysMenus(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}

    /*@Override
    public List<String> queryAdminPerms() {
        List<SysMenu> menuList = baseMapper.selectList(null);
        List<String>permsList = new ArrayList<>(menuList.size());
        for (SysMenu menu : menuList) {
            permsList.add(menu.getPerms());
        }
        return permsList;
    }

    @Override
    public List<String> queryUserPerms(String userId) {
        return sysUserService.queryAllPerms(userId);
    }*/

}
