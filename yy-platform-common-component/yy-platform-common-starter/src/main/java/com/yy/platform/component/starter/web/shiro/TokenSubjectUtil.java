package com.yy.platform.component.starter.web.shiro;

import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * TODO 改造通过cache 获取 subject
 */
@Component
public class TokenSubjectUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    //private static Map<String, Subject> subjectMap = new HashMap<>();

    public void saveUser(String randomKey,LoginUserInfo userDetail) {
        ValueOperations<String, LoginUserInfo> operations = redisTemplate.opsForValue();
        operations.set(randomKey,userDetail);
       // subjectMap.put(randomKey, sub);
    }

    public LoginUserInfo getUser(String key) {
        ValueOperations<String, LoginUserInfo> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public Boolean removeUser(String key) {
        return redisTemplate.delete(key);
    }


    public void save(String key, String value){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key,value);
    }

    public void save(String key, String value,long time){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key,value);
        //验证码过期时间
        redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
    }

    public String get(String key){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public Boolean remove(String key) {
        return redisTemplate.delete(key);
    }

}
