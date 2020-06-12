package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysDataPerm;
import com.yy.platform.system.management.entity.SysMenuDataPerm;

import java.util.List;

/**
 * 菜单-数据权限管理
 *
 */
public interface SysMenuDataPermService extends IService<SysMenuDataPerm> {

    /**
     * 保存数据权限与菜单的关系
     * @param perm
     */
    boolean saveOrUpdate(SysDataPerm perm);

    /**
     * 通过菜单id查询数据权限列表
     * @param menuId
     * @return
     */
    List<String> queryPermIds(String menuId);

    /**
     * 批量删除
     * @param apiPerms
     * @return
     */
    int deleteBatch(String[] apiPerms);
}
