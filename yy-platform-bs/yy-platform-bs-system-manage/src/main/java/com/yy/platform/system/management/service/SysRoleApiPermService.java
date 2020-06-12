package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysMenuApiPerm;
import com.yy.platform.system.management.entity.SysRoleApiPerm;

import java.util.List;

/**
 * 菜单-接口权限管理
 *
 */
public interface SysRoleApiPermService extends IService<SysRoleApiPerm> {

    /**
     * 通过角色id查询接口权限列表
     * @param roleIds
     * @return
     */
    List<String> queryPermIds(List<String> roleIds);

    /**
     * 批量删除
     * @param perms
     * @return
     */
    int deleteBatch(String[] perms);
}
