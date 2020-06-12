package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysMenuApiPerm;

import java.util.List;

/**
 * 菜单-接口权限管理
 *
 */
public interface SysMenuApiPermService extends IService<SysMenuApiPerm> {

    /**
     * 保存接口权限与菜单的关系
     * @param sysApiPerm
     */
    boolean saveOrUpdate(SysApiPerm sysApiPerm);

    /**
     * 通过菜单id查询接口权限列表
     * @param menuId
     * @return
     */
    List<String> queryApiPermIds(String menuId);

    /**
     * 批量删除
     * @param apiPerms
     * @return
     */
    int deleteBatch(String[] apiPerms);
}
