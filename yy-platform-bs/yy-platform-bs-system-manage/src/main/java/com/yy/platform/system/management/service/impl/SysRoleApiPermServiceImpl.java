package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.system.management.dao.SysRoleApiPermDao;
import com.yy.platform.system.management.entity.SysApiPerm;
import com.yy.platform.system.management.entity.SysRoleApiPerm;
import com.yy.platform.system.management.entity.SysRoleMenu;
import com.yy.platform.system.management.service.SysApiPermService;
import com.yy.platform.system.management.service.SysRoleApiPermService;
import com.yy.platform.system.management.utils.ToolForID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 */
@Service("sysRoleApiPermService")
public class SysRoleApiPermServiceImpl extends ServiceImpl<SysRoleApiPermDao, SysRoleApiPerm> implements SysRoleApiPermService {

	@Override
	public void saveOrUpdate(String roleId, List<String> apiPermList) {
		//先删除角色与菜单关系
		deleteBatch(new String[]{roleId});

		if(apiPermList == null || apiPermList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		List<SysRoleApiPerm> list = new ArrayList<>(apiPermList.size());
		for(String apiPermId : apiPermList){

			SysRoleApiPerm sysRoleApiPerm = new SysRoleApiPerm();
			sysRoleApiPerm.setId(ToolForID.getSysRoleApiPermId());
			sysRoleApiPerm.setApiPermId(apiPermId);
			sysRoleApiPerm.setRoleId(roleId);

			list.add(sysRoleApiPerm);
		}
		this.saveBatch(list);
	}

	@Override
	public List<String> queryPermIds(List<String> roleIds) {
		List<SysRoleApiPerm> sysRoleApiPerms = baseMapper.selectList(new QueryWrapper<SysRoleApiPerm>().in("role_id",roleIds));
		Set<String> sysApiPermIds = sysRoleApiPerms.stream().map(x -> x.getApiPermId()).collect(Collectors.toSet());
		return new ArrayList<>(sysApiPermIds);
	}

	@Override
	public int deleteBatch(String[] roleId){
		return baseMapper.delete(new QueryWrapper<SysRoleApiPerm>().in("ROLE_ID",roleId));
	}

}
