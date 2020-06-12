package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.system.management.dao.SysApiPermDao;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.service.SysApiPermService;
import com.yy.platform.system.management.service.SysMenuApiPermService;
import com.yy.platform.system.management.service.SysRoleApiPermService;
import com.yy.platform.system.management.utils.ToolForID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("sysApiPermService")
public class SysApiPermServiceImpl extends ServiceImpl<SysApiPermDao, SysApiPerm> implements SysApiPermService {

    @Autowired
    private SysMenuApiPermService sysMenuApiPermService;

    @Autowired
    private SysRoleApiPermService sysRoleApiPermService;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="api",allEntries=true)
    public boolean save(SysApiPerm sysApiPerm) {
        sysApiPerm.setCreateTime(new Date());
        sysApiPerm.setId(ToolForID.getSysApiPermID());
        int flag = baseMapper.insert(sysApiPerm);
        //保存菜单与接口权限的关系
        sysMenuApiPermService.saveOrUpdate(sysApiPerm);

        return flag > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="api",allEntries=true)
    public void deleteBatch(String[] apiPerms) {
        // 接口权限
        this.removeByIds(Arrays.asList(apiPerms));

        // 删除接口权限与菜单关联
        sysMenuApiPermService.deleteBatch(apiPerms);

        //删除接口权限与角色关联
        sysRoleApiPermService.deleteBatch(apiPerms);
    }

    /**
     * 通过id获取接口权限值
     * @param ids
     * @return
     */
    @Override
    public List<String> queryAllPerms(List<String> ids) {
        if(ids.size() <= 0){
            return new ArrayList<>();
        }
        List<SysApiPerm> apiPermList = this.listByIds(ids);
        List<String> permList = apiPermList.stream().map(x -> x.getPerm()).collect(Collectors.toList());
        return permList;
    }


}
