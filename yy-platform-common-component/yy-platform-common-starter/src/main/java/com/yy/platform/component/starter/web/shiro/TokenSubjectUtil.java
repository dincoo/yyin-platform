package com.yy.platform.component.starter.web.shiro;

import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


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
}
