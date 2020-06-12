package com.yy.platform.system.management.utils;

public class ShiroUtils {

    //获取当前用户id
    public static String getUserId() {
        return "1";
    }
    public static String sha256(String password, String salt) {
        // TODO: 2020/6/8 后面要加盐
        return password;
        //return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }
}
