package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.component.starter.orm.util.Query;
import com.yy.platform.component.starter.result.pager.PageResult;
import com.yy.platform.system.management.contants.Constant;
import com.yy.platform.system.management.dao.SysUserDao;
import com.yy.platform.system.management.entity.SysUser;
import com.yy.platform.system.management.service.*;
import com.yy.platform.system.management.utils.ShiroUtils1;
import com.yy.platform.system.management.utils.ToolForID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;


/**
 * 系统用户
 * 
 * @author jaden
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService{

    /** 加密算法 */
    public final static String hashAlgorithmName = "SHA-256";
    /** 循环次数 */
    public final static int hashIterations = 16;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    /*@Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysConfigDao sysConfigDao;*/

    @Autowired
    private SysRoleApiPermService sysRoleApiPermService;

    @Autowired
    private SysApiPermService sysApiPermService;

    @Autowired
    private SysDataPermService sysDataPermService;

    @Autowired
    private SysRoleDataPermService sysRoleDataPermService;

    /**
     * 查询用户所有菜单id
     * @param userId
     * @return
     */
    @Override
    //@Cacheable(value = "users",key = "#userId + 'queryAllMenuId'")
    public List<String> queryAllMenuId(String userId) {
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        List<String> menuIdList = sysRoleMenuService.queryMenuIdListByRoleIds(roleIdList);
        return menuIdList;
    }

    /**
     * 查询用户所有角色
     * @param userId
     * @return
     */
    @Override
    public List<String> queryRoles(String userId) {
        return sysUserRoleService.queryRoleIdList(userId);
    }

    /**
     * 查询用户所以接口权限
     * @param userId  用户ID
     * @return
     */
    @Override
    //@Cacheable(value = "users",key = "#userId + 'queryAllPerms'")
    public List<String> queryApiPerms(String userId) {
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        List<String> apiPermIdList = sysRoleApiPermService.queryPermIds(roleIdList);
        List<String> permList = sysApiPermService.queryAllPerms(apiPermIdList);
        return permList;
    }

    /**
     * 查询数据权限id
     * @param userId
     * @return
     */
    @Override
    public List<String> queryDataPerms(String userId) {
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        List<String> dataPermIdList = sysRoleDataPermService.queryPermIds(roleIdList);
        return dataPermIdList;
    }

    @Override
    // @DataFilter(subDept = true, user = false)
    //@Cacheable(value = "users",key = "#params + 'queryPage'")
    public PageResult<SysUser> queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Page<SysUser> page = baseMapper.selectPage(new Query<SysUser>(params).getPage(), new QueryWrapper<SysUser>()
                .ne("id_","1")
                .like(StringUtils.isNotBlank(username), "username", username)
                .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
                .orderByDesc("CREATE_TIME"));

        return PageResult.<SysUser>builder()
                .data(page.getRecords())
                .count(page.getTotal())
                .current(page.getCurrent())
                .size(page.getSize())
                .build();
    }

    @Override
    //@CacheEvict(value="users",allEntries=true)
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysUser user) {
        user.setCreateTime(new Date());
        user.setId(ToolForID.getSysUserID());
        user.setPasswd(ShiroUtils1.sha256(user.getPasswd(), "SALT"));
        int insertFlag = baseMapper.insert(user);
        // 保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
        return insertFlag > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value="users",allEntries=true)
    public void update(SysUser user) {
        if (StringUtils.isBlank(user.getPasswd())) {
            user.setPasswd(null);
        } else {
            user.setPasswd(ShiroUtils1.sha256(user.getPasswd(), "SALT"));
        }
        user.setUpdateTime(new Date());
        this.updateById(user);
        // 保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    @CacheEvict(value="users",allEntries=true)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @CacheEvict(value="users",allEntries=true)
    public boolean updatePassword(String userId, String password, String newPassword) {
        SysUser userEntity = new SysUser();
        userEntity.setPasswd(newPassword);
        return this.update(userEntity, new QueryWrapper<SysUser>().eq("id_", userId).eq("passwd", password));
    }

    @Override
    @CacheEvict(value="users",allEntries=true)
    public boolean resetBatchIds(List<String> idList) {
        List<SysUser> userList = new ArrayList<>();
        idList.forEach(id -> {
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            //sysUser.setPasswd(sha256("abcd12345", "SALT"));
            sysUser.setPasswd(ShiroUtils1.sha256("abcd12345", "SALT"));
            userList.add(sysUser);
        });
        return super.updateBatchById(userList);
    }



    /*@Override
    public ISysUser selectByUserName(String userName) {
        QueryWrapper<ISysUser> qw = new QueryWrapper<>();
        qw.eq("USERNAME", userName);
        ISysUser user = baseMapper.selectOne(qw);
        return user;
    }
    
    @Override
    public List<ISysUser> getUserListWithAccount() {
        QueryWrapper<SysConfig> qw = new QueryWrapper<>();
        qw.eq("KEY", "ADMIN_ROLE");
        List<SysConfig> configs = sysConfigDao.selectList(qw);
        List<String> roleIds = new ArrayList<>();
        if(!configs.isEmpty()){
            configs.forEach(config->{
                roleIds.add(config.getValue());
            }); 
        }
        return baseMapper.selectUserList(roleIds);
    }*/

}
