package com.yy.platform.component.starter.web.shiro;

import com.google.common.collect.Lists;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HmacRealm extends AuthorizingRealm {

    public Class<?> getAuthenticationTokenClass() {
        return HmacToken.class;//此Realm只支持HmacToken
    }

    /**
     *  认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        HmacToken hmacToken = (HmacToken)token;

        // TODO 校验Token有效性
//        List<String> keys = Lists.newArrayList();
//        for (String key:hmacToken.getParameters().keySet()){
//            if (!"digest".equals(key))
//                keys.add(key);
//        }
//        Collections.sort(keys);//对请求参数进行排序参数->自然顺序
//        StringBuffer baseString = new StringBuffer();
//        for (String key : keys) {
//            baseString.append(hmacToken.getParameters().get(key)[0]);
//        }
//        //认证端生成摘要
//        String serverDigest = cryptogramService.hmacDigest(baseString.toString());
//        //客户端请求的摘要和服务端生成的摘要不同
//        if(!serverDigest.equals(hmacToken.getDigest())){
//            throw new AuthenticationException("数字摘要验证失败！！！");
//        }
//        Long visitTimeStamp = Long.valueOf(hmacToken.getTimeStamp());
//        Long nowTimeStamp = System.currentTimeMillis();
//        Long jge = nowTimeStamp - visitTimeStamp;
//        if (jge > 600000) {// 十分钟之前的时间戳，这是有效期可以双方约定由参数传过来
//            throw new AuthenticationException("数字摘要失效！！！");
//        }
        // 此处可以添加查询数据库检查账号是否存在、是否被锁定、是否被禁用等等逻辑
        return new SimpleAuthenticationInfo(hmacToken.getClientKey(),Boolean.TRUE,getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String clientKey = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        // 根据客户标识（可以是用户名、app id等等） 查询并设置角色
//        Set<String> roles = accountProvider.loadRoles(clientKey);
//        info.setRoles(roles);
        // 根据客户标识（可以是用户名、app id等等） 查询并设置权限
        Set<String> permissions = new HashSet<>();
        info.setStringPermissions(permissions);
        return info;
    }
}
