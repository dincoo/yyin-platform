package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.yy.platform.component.starter.exception.ApiException;
import com.yy.platform.system.management.dao.SysMenuDataPermDao;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysDataPerm;
import com.yy.platform.system.management.entity.SysMenuApiPerm;
import com.yy.platform.system.management.entity.SysMenuDataPerm;
import com.yy.platform.system.management.service.SysMenuDataPermService;
import com.yy.platform.system.management.utils.ToolForID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysMenuDataPermService")
public class SysMenuDataPermServiceImpl extends ServiceImpl<SysMenuDataPermDao, SysMenuDataPerm> implements SysMenuDataPermService {

    @Override
    public boolean saveOrUpdate(SysDataPerm perm) {
        //先删除接口权限与菜单的关系
        Map<String, Object> param = CollectionUtil.newHashMap();
        param.put("DATA_PERM_ID", perm.getId());
        this.removeByMap(param);

        if(StringUtils.isBlank(perm.getMenuId())){
            throw new ApiException("400","菜单id不能为空");
        }

        //保存菜单与权限的关系
        SysMenuDataPerm menuPerm = new SysMenuDataPerm();
        menuPerm.setId(ToolForID.getSysMenuApiPermID());
        menuPerm.setMenuId(perm.getMenuId());
        menuPerm.setDataPermId(perm.getId());
        menuPerm.setCreateTime(new Date());
        return this.save(menuPerm);

    }

    /**
     * 通过菜单id查询数据权限列表
     * @param menuId
     * @return
     */
    @Override
    public List<String> queryPermIds(String menuId) {
        List<SysMenuDataPerm> sysMenuPerms = baseMapper.selectList(new QueryWrapper<SysMenuDataPerm>().eq("menu_id",menuId));
        List<String> sysPermIds = sysMenuPerms.stream().map(x -> x.getDataPermId()).collect(Collectors.toList());
        return sysPermIds;
    }

    /**
     * 批量删除
     * @param perms
     * @return
     */
    @Override
    public int deleteBatch(String[] perms) {
        return baseMapper.delete(new QueryWrapper<SysMenuDataPerm>().in("DATA_PERM_Id",perms));
    }
}
