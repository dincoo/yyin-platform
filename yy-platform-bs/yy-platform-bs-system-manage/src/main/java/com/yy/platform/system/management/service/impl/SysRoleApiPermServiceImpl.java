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
import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 */
@Service("sysRoleApiPermService")
public class SysRoleApiPermServiceImpl extends ServiceImpl<SysRoleApiPermDao, SysRoleApiPerm> implements SysRoleApiPermService {

	@Override
	public List<String> queryPermIds(List<String> roleIds) {
		List<SysRoleApiPerm> sysRoleApiPerms = baseMapper.selectList(new QueryWrapper<SysRoleApiPerm>().in("role_id",roleIds));
		List<String> sysApiPermIds = sysRoleApiPerms.stream().map(x -> x.getApiPermId()).collect(Collectors.toList());
		return sysApiPermIds;
	}

	@Override
	public int deleteBatch(String[] perms){
		return baseMapper.delete(new QueryWrapper<SysRoleApiPerm>().in("API_PERM_ID",perms));
	}

}
