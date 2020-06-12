package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.component.starter.orm.util.Query;
import com.yy.platform.component.starter.result.pager.PageResult;
import com.yy.platform.system.management.contants.Constant;
import com.yy.platform.system.management.dao.SysRoleDao;
import com.yy.platform.system.management.entity.SysRole;
import com.yy.platform.system.management.entity.SysUser;
import com.yy.platform.system.management.service.SysRoleMenuService;
import com.yy.platform.system.management.service.SysRoleService;
import com.yy.platform.system.management.service.SysUserRoleService;
import com.yy.platform.system.management.utils.ToolForID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    //@Autowired
    //private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
   // @Autowired
    //private SysDeptService sysDeptService;

    @Override
    // @DataFilter(subDept = true, user = false)
    @Cacheable(value = "roles",key = "#params + 'queryPage'")
    public PageResult<SysUser> queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");

        Page<SysRole> page = baseMapper.selectPage(new Query<SysRole>(params).getPage(), new QueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
                .orderByDesc("CREATE_TIME"));

        return PageResult.<SysUser>builder()
                .data(page.getRecords())
                .count(page.getTotal())
                .current(page.getCurrent())
                .size(page.getSize())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="roles",allEntries=true)
    public boolean save(SysRole role) {
        role.setCreateTime(new Date());
        role.setId(ToolForID.getSysRoleID());
        int flag = baseMapper.insert(role);

        // 保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());

        // 保存角色与部门关系
        //sysRoleDeptService.saveOrUpdate(role.getId(), role.getDeptIdList());
        return flag > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="roles",allEntries=true)
    public void update(SysRole role) {
        role.setUpdateTime(new Date());
        this.updateById(role);

        // 更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());

        // 保存角色与部门关系
        //sysRoleDeptService.saveOrUpdate(role.getId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="roles",allEntries=true)
    public void deleteBatch(String[] roleIds) {
        // 删除角色
        this.removeByIds(Arrays.asList(roleIds));

        // 删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        // 删除角色与部门关联
        //sysRoleDeptService.deleteBatch(roleIds);

        // 删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }
}
