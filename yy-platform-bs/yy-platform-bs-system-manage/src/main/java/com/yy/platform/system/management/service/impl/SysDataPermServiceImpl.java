package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.system.management.dao.SysDataPermDao;
import com.yy.platform.system.management.entity.SysDataPerm;
import com.yy.platform.system.management.service.*;
import com.yy.platform.system.management.utils.ToolForID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("sysDataPermService")
public class SysDataPermServiceImpl extends ServiceImpl<SysDataPermDao, SysDataPerm> implements SysDataPermService {

    @Autowired
    private SysMenuDataPermService sysMenuDataPermService;

    @Autowired
    private SysRoleDataPermService sysRoleDataPermService;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="dataperm",allEntries=true)
    public boolean save(SysDataPerm perms) {
        perms.setCreateTime(new Date());
        perms.setId(ToolForID.getSysApiPermID());
        int flag = baseMapper.insert(perms);
        //保存菜单与接口权限的关系
        sysMenuDataPermService.saveOrUpdate(perms);

        return flag > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="dataperm",allEntries=true)
    public void deleteBatch(String[] perms) {
        // 接口权限
        this.removeByIds(Arrays.asList(perms));

        // 删除数据权限与菜单关联
        sysMenuDataPermService.deleteBatch(perms);

        //删除数据权限与角色关联
        sysRoleDataPermService.deleteBatch(perms);
    }


}
