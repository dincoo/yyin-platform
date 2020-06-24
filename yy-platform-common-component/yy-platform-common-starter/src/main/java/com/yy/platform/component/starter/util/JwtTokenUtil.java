package com.yy.platform.component.starter.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yy.platform.component.starter.exception.AuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Date;

/**
 * @Auther: zhanqingqi
 * @Date: 2019/12/17 17:46
 * @Description: JwtToken工具
 */
public class JwtTokenUtil {

    /**
     * 加密盐
     */
    @Value("${yy.platform.jwt.secret:yuyin_jlx}")
    private String secret = "yuyin_jlx";

    @Value("${yy.platform.jwt.issuer:yuyin_jlx}")
    private String issuer = "yuyin_jlx";

    private static final String CLAIM_KEY_USERID = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 过期时间
     */
    private static  final long EXPIRE_TIME= 60*60*1000; // 一个小时
    /**
     * 使用用户唯一标识创建token
     * @param userId
     * @return
     */
    public String createTokenByUserId(String userId){

        return JWT.create().withIssuer(issuer)
                .withExpiresAt(generateExpirationDate())
                .withClaim(CLAIM_KEY_USERID,userId)
                .withClaim(CLAIM_KEY_CREATED, new Date())
                .sign(Algorithm.HMAC512(secret));
    }

    /**
     * token过期时间
     * @return
     */
    private static Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+EXPIRE_TIME);
    }

    /**
     * token校验是否生效
     * @param token
     */
    public boolean verifyToken(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret))
                .withIssuer(issuer)
                .build();
        try{
            DecodedJWT jwt = verifier.verify(token);
        }catch (JWTVerificationException e){
            return false;
        }
        return true;

    }

    /**
     * 解密token获取用户ID
     * @param token
     * @return
     */
    public static String getUserIdFromToken(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(CLAIM_KEY_USERID).asString();
    }

    /**
     * token校验
     * @param token
     */
    public void checkToken(String token){
        if(StringUtils.isBlank(token)){
            throw new AuthException("无token,非法请求", "checkToken");
        }
        try {
            if(!this.verifyToken(token)){
                throw new AuthException("401","登录超时，请重新登录", "checkToken","鉴权失败");
            }
        } catch (TokenExpiredException e) {
            throw new AuthException("登录超时，请重新登录", "checkToken");
        } catch (Exception e){
            throw new AuthException("401","登录超时，请重新登录", "checkToken","鉴权失败");
        }
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.createTokenByUserId("2");
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEyMzQ1NiwiY3JlYXRlZCI6MTU3NjcxNTYxNSwiaXNzIjoibWUtbWFsbCIsImV4cCI6MTU3NjcxNTkxNX0.d6-35QxglH_v_BND71n3Z9j2jtKWwzhnmtIBzKNPMVuTzFE27A8OyjjrTEte98wBUfc8LlrLBYLkSFBexfVOgw";
        System.out.println(token);
        try {
            System.out.println("开始处理");
            jwtTokenUtil.verifyToken(token);
            System.out.println(JwtTokenUtil.getUserIdFromToken(token));
        } catch (Exception e) {
            System.out.println("失败");
            e.printStackTrace();
        }

    }


}
