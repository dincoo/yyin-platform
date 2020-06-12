package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.yy.platform.component.starter.exception.ApiException;
import com.yy.platform.component.starter.exception.BaseCustomizeException;
import com.yy.platform.system.management.dao.SysMenuApiPermDao;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysMenuApiPerm;
import com.yy.platform.system.management.service.SysMenuApiPermService;
import com.yy.platform.system.management.utils.ToolForID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysMenuApiPermService")
public class SysMenuApiPermServiceImpl extends ServiceImpl<SysMenuApiPermDao, SysMenuApiPerm> implements SysMenuApiPermService {

    @Override
    public boolean saveOrUpdate(SysApiPerm sysApiPerm) {
        //先删除接口权限与菜单的关系
        Map<String, Object> param = CollectionUtil.newHashMap();
        param.put("API_PERM_ID", sysApiPerm.getId());
        this.removeByMap(param);

        if(StringUtils.isBlank(sysApiPerm.getMenuId())){
            throw new ApiException("400","菜单id不能为空");
        }

        //保存菜单与权限的关系
        SysMenuApiPerm sysMenuApiPerm = new SysMenuApiPerm();
        sysMenuApiPerm.setId(ToolForID.getSysMenuApiPermID());
        sysMenuApiPerm.setMenuId(sysApiPerm.getMenuId());
        sysMenuApiPerm.setApiPermId(sysApiPerm.getId());
        sysMenuApiPerm.setCreateTime(new Date());
        return this.save(sysMenuApiPerm);

    }

    /**
     * 通过菜单id查询接口权限列表
     * @param menuId
     * @return
     */
    @Override
    public List<String> queryApiPermIds(String menuId) {
        List<SysMenuApiPerm> sysMenuApiPerms = baseMapper.selectList(new QueryWrapper<SysMenuApiPerm>().eq("menu_id",menuId));
        List<String> sysApiPermIds = sysMenuApiPerms.stream().map(x -> x.getApiPermId()).collect(Collectors.toList());
        return sysApiPermIds;
    }

    /**
     * 批量删除
     * @param apiPerms
     * @return
     */
    @Override
    public int deleteBatch(String[] apiPerms) {
        return baseMapper.delete(new QueryWrapper<SysMenuApiPerm>().in("API_PERM_Id",apiPerms));
    }
}
