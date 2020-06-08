package com.yy.platform.component.starter.web.auth;

import com.yy.platform.component.starter.web.auth.model.SysUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: zhongdh
 * @Date: 2020/1/2 10:18
 * @Description: 系统用户安全上下文信息
 */
@Slf4j
public class SecurityContext {

    /**
     * 当前UserId
     */
    private static ThreadLocal<Long> userIdLocal = new ThreadLocal<>();

    /**
     * 当前用户
     */
    private static ThreadLocal<SysUser> userLocal = new ThreadLocal<>();

    private SecurityContext() {
    }

    public static SysUser getUserPrincipal(){
        return userLocal.get();
    }

    public static void setUserPrincipal(SysUser user){
        userLocal.set(user);
    }

    public static void setUserId(Long userId){
        userIdLocal.set(userId);
    }

    public static Long getUserId(){
        return userIdLocal.get();
    }

    public static void cleanContext(){
        userIdLocal.remove();
        userLocal.remove();
    }
}
