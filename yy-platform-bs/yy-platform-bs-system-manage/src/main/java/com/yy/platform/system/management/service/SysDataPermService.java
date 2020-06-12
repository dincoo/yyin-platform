package com.yy.platform.system.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysDataPerm;

import java.util.List;

/**
 * 接口权限管理
 *
 */
public interface SysDataPermService extends IService<SysDataPerm> {

    /**
     * 保存
     * @param sysDataPerm
     * @return
     */
    boolean save(SysDataPerm sysDataPerm);

    /**
     * 批量删除
     * @param dataPerms
     */
    void deleteBatch(String[] dataPerms);

}
