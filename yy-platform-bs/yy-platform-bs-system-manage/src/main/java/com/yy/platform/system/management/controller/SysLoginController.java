package com.yy.platform.system.management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.annotation.LoginUser;
import com.yy.platform.component.starter.web.auth.model.ISysUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.component.starter.web.shiro.HmacToken;
import com.yy.platform.component.starter.web.shiro.TokenSubjectUtil;
import com.yy.platform.system.management.contants.Constant;
import com.yy.platform.system.management.entity.SysUser;
import com.yy.platform.system.management.service.*;
import com.yy.platform.system.management.utils.ShiroUtils;
import com.yy.platform.system.management.utils.ShiroUtils1;
import com.yy.platform.system.management.vo.SysLoginVo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 登录相关
 *
 */
@RestController
public class SysLoginController {



    private static final String CAPTCHA = "_captcha";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenSubjectUtil tokenSubjectUtil;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysApiPermService sysApiPermService;

    @Autowired
    private SysDataPermService sysDataPermService;

    @Autowired
    private Producer producer;

    @ApiOperation(value = "登陆验证码获取")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        try {
            String ip = request.getRemoteAddr();

            tokenSubjectUtil.save(ip + CAPTCHA,text,60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R login(HttpServletRequest request,@RequestBody @Validated SysLoginVo sysLoginVo) throws Exception {
        String ip = request.getRemoteAddr();
        //暂不校验
        /*String kaptcha = tokenSubjectUtil.get(ip + CAPTCHA);
        if(!"1".equals(sysLoginVo.getCaptcha())){//测试使用
            if(kaptcha == null){
                return R.Builder.badReq().message("验证码失效").build();
            }
            if(!sysLoginVo.getCaptcha().equalsIgnoreCase(kaptcha)){
                return R.Builder.badReq().message("验证码不正确").build();
            }
        }*/

        String username = sysLoginVo.getUserName();
        String password = sysLoginVo.getPasswd();
        password = ShiroUtils1.sha256(password,"SALT");
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("USERNAME",username));
        if(sysUser == null){
            return R.Builder.badReq().message("用户名错误").build();
        }
        if(!sysUser.getPasswd().equals(password)){
            return R.Builder.badReq().message("密码错误").build();
        }
        ISysUser shiroSysUser = new ISysUser();
        BeanUtils.copyProperties(sysUser,shiroSysUser);

        JwtTokenUtil jwtTokenUtil= new JwtTokenUtil();
        String clientKey = jwtTokenUtil.createTokenByUserId(shiroSysUser.getId());



        //创建令牌
        Subject subject =  SecurityUtils.getSubject();
        AuthenticationToken token = new HmacToken(clientKey, "2020-06-06 10:20:21", "digest", "host",null);
        // 执行认证登陆
        subject.login(token);
        //saveSession(sysUser.getId(),subject,clientKey);
        cacheUser(shiroSysUser.getId(), username, clientKey);

        //tokenSubjectUtil.saveSession(clientKey, subject.getSession());
        Map resultMap = new HashMap();
        resultMap.put("token", clientKey);
        return R.Builder.success(resultMap).build();
    }

    /**
     * 将用户进行缓存
     * @param userId
     * @param clientKey
     */
    private void cacheUser(String userId,String userName, String clientKey) {
        LoginUserInfo userDetail = new LoginUserInfo();
        userDetail.setId(userId);
        userDetail.setName(userName);
        userDetail.setRoles(sysUserService.queryRoles(userId));
        if (Constant.SUPER_ADMIN.equals(userId)) {
            userDetail.setMenus(sysMenuService.list().stream().map(menu->menu.getId()).collect(Collectors.toList()));
            userDetail.setApiPerms(sysApiPermService.list().stream().map(apiPerm-> apiPerm.getPerm()).collect(Collectors.toList()));
            userDetail.setDataPerms(sysDataPermService.list().stream().map(dataPerm->dataPerm.getId()).collect(Collectors.toList()));
        }else {
            userDetail.setMenus(sysUserService.queryAllMenuId(userId));
            userDetail.setApiPerms(sysUserService.queryApiPerms(userId));
            userDetail.setDataPerms(sysUserService.queryDataPerms(userId));
        }

        userDetail.setToken(clientKey);
        tokenSubjectUtil.saveUser(userId,userDetail);
    }

    /**
     * 退出
     */
    @ResponseBody
    @GetMapping("/sys/logout")
    public R logout(@LoginUser LoginUserInfo loginUserInfo) {
        if(tokenSubjectUtil.removeUser(loginUserInfo.getId())){
            return R.Builder.success().message("退出成功").build();
        }
        return R.Builder.failure().message("系统异常").build();
    }


}
