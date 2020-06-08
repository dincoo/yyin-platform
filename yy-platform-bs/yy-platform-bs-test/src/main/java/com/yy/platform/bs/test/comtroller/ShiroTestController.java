package com.yy.platform.bs.test.comtroller;

import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.shiro.HmacToken;
import com.yy.platform.component.starter.web.shiro.TokenSubjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class ShiroTestController {

    @RequestMapping("/shiro/auth")
    @RequiresPermissions("a:b")
    public R shiroAuth(){
        log.info("into ShiroTestController shiroAuth");
        return R.Builder.success("ok").build();
    }

    @RequestMapping(path = "login")
    public R login() throws Exception {
        String username = "123";
        String password = "abc";

        JwtTokenUtil jwtTokenUtil= new JwtTokenUtil();
        String clientKey = jwtTokenUtil.createTokenByUserId(123456987L);

        //创建令牌
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new HmacToken(clientKey, "2020-06-06 10:20:21", "digest", "host",null);
        // 执行认证登陆
        subject.login(token);

        Map resultMap = new HashMap();
        resultMap.put("token", clientKey);
        TokenSubjectUtil.saveSubject(clientKey, subject);
        return R.Builder.success(resultMap).build();
    }



}
