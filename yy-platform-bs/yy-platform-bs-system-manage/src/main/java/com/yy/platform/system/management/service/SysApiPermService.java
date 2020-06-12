package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysApiPerm;

import java.util.List;

/**
 * 接口权限管理
 *
 */
public interface SysApiPermService extends IService<SysApiPerm> {

    /**
     * 保存
     * @param sysApiPerm
     * @return
     */
    boolean save(SysApiPerm sysApiPerm);

    /**
     * 批量删除
     * @param apiPerms
     */
    void deleteBatch(String[] apiPerms);

    /**
     * 通过id获取权限数据
     * @param ids
     * @return
     */
    List<String> queryAllPerms(List<String> ids);
}
