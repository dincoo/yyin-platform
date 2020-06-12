package com.yy.platform.system.management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.util.JwtTokenUtil;
import com.yy.platform.component.starter.web.auth.model.ISysUser;
import com.yy.platform.component.starter.web.auth.model.LoginUserInfo;
import com.yy.platform.component.starter.web.shiro.HmacToken;
import com.yy.platform.component.starter.web.shiro.TokenSubjectUtil;
import com.yy.platform.system.management.entity.SysUser;
import com.yy.platform.system.management.service.SysRoleService;
import com.yy.platform.system.management.service.SysUserService;
import com.yy.platform.system.management.utils.ShiroUtils;
import com.yy.platform.system.management.vo.SysLoginVo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 登录相关
 *
 */
@RestController
public class SysLoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenSubjectUtil tokenSubjectUtil;

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R login(@RequestBody @Validated SysLoginVo sysLoginVo) throws Exception {
        //TODO 验证码校验


        String username = sysLoginVo.getUserName();
        String password = sysLoginVo.getPasswd();
        password = ShiroUtils.sha256(password,"SALT");
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
        userDetail.setMenus(sysUserService.queryAllMenuId(userId));
        userDetail.setApiPerms(sysUserService.queryApiPerms(userId));
        userDetail.setDataPerms(sysUserService.queryDataPerms(userId));
        userDetail.setToken(clientKey);
        tokenSubjectUtil.saveUser(userId,userDetail);
    }

    /*@Cacheable(value="userId",key = "#userId")
    public String saveSession(String userId, Subject subject ,String clientKey){

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roles",new HashSet<>(sysUserService.queryRoles(userId)));
        jsonObject.put("menuIds",new HashSet<>(sysUserService.queryAllMenuId(userId)));
        jsonObject.put("apiPerms",new HashSet<>(sysUserService.queryApiPerms(userId)));
        jsonObject.put("dataPerms",new HashSet<>(sysUserService.queryDataPerms(userId)));
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(userId,jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }*/

    /*@Autowired
    private Producer producer;

    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        WebUtil.setSession(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    *//**
     * 登录
     *//*
    @ResponseBody
    @PostMapping("/sys/login")
    @IgnoreAuth
    public Object login(ModelMap modelMap, @RequestBody SysLoginVo sysLoginVo) {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        //if (!sysLoginVo.getCaptcha().equalsIgnoreCase(kaptcha)) {
        //    return setModelMap(modelMap, HttpCode.LOGIN_FAIL, "验证码不正确");
        //}
        try {
            Subject subject = ShiroUtils.getSubject();
            // TODO: 登录信息校验 2020/6/3
			//UsernamePasswordToken token1 = new UsernamePasswordToken(sysLoginVo.getUserName(), sysLoginVo.getPasswd());
            HybridToken token = new HybridToken(sysLoginVo.getUserName(), sysLoginVo.getPasswd());
            subject.login(token);
        } catch (UnknownAccountException e) {
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL, "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL, "密码有误");
        } catch (LockedAccountException e) {
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL, "用户无效，请联系管理员");
        } catch (AuthenticationException e) {
            return setModelMap(modelMap, HttpCode.LOGIN_FAIL, "用户无效，请联系管理员");
        }

        String secureKey = OnlyCodeGenerator.sessionSecuretKey();
        ISysUser sysUser = (ISysUser) WebUtil.getCurrentUser();
       // sysUser.setSecretKey(secureKey);

        WebUtil.saveCurrentUser(sysUser);

        return setSuccessModelMap(secureKey);
    }

    *//**
     * 退出
     *//*
    @ResponseBody
    @RequestMapping(value = "/sys/logout", method = RequestMethod.GET)
    public Object logout(HttpServletRequest request) {
        WebUtil.removeCurrentUser(request);
        ShiroUtils.logout();
        return setSuccessModelMap(1);
    }*/

}
