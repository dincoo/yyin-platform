package com.yy.platform.component.starter.web.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yy.platform.component.starter.exception.AuthException;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class HmacRealm extends AuthorizingRealm {

    public Class<?> getAuthenticationTokenClass() {
        return HmacToken.class;//此Realm只支持HmacToken
    }

    private final static JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Autowired
    private TokenSubjectUtil tokenSubjectUtil;

    /**
     *  认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        HmacToken hmacToken = (HmacToken)token;

        // 校验Token有效性
        jwtTokenUtil.checkToken(hmacToken.getClientKey());

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
        LoginUserInfo userDetail = null;
        String userId = JwtTokenUtil.getUserIdFromToken(clientKey);
        if(StringUtils.isBlank(userId)){
            new AuthException("无效token", "checkToken");
        }
        userDetail = tokenSubjectUtil.getUser(userId);
        if(userDetail == null){
            new AuthException("token失效，请重新登录", "checkToken");
        }

        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();

        //获取角色与权限的内容
        info.setStringPermissions(new HashSet<>(userDetail.getApiPerms()));
        info.setRoles(new HashSet<>(userDetail.getRoles()));
        return info;
    }


}
