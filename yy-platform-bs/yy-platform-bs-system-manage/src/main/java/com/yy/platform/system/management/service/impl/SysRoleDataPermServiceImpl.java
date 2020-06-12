package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.system.management.dao.SysRoleDataPermDao;
import com.yy.platform.system.management.entity.SysRoleApiPerm;
import com.yy.platform.system.management.entity.SysRoleDataPerm;
import com.yy.platform.system.management.service.SysRoleDataPermService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 */
@Service("sysRoleDataPermService")
public class SysRoleDataPermServiceImpl extends ServiceImpl<SysRoleDataPermDao, SysRoleDataPerm> implements SysRoleDataPermService {

	@Override
	public List<String> queryPermIds(List<String> roleIds) {
		List<SysRoleDataPerm> sysRoleDataPerms = baseMapper.selectList(new QueryWrapper<SysRoleDataPerm>().in("role_id",roleIds));
		List<String> permIds = sysRoleDataPerms.stream().map(x -> x.getDataPermId()).collect(Collectors.toList());
		return permIds;
	}

	@Override
	public int deleteBatch(String[] perms){
		return baseMapper.delete(new QueryWrapper<SysRoleDataPerm>().in("DATA_PERM_ID",perms));
	}

}
