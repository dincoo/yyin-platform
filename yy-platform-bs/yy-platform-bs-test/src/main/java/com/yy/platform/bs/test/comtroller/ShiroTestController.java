package com.yy.platform.bs.test.comtroller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yy.platform.bs.test.entity.GoodsEntity;
import com.yy.platform.bs.test.service.IGoodsService;
import com.yy.platform.component.starter.constants.CommonConstant;
import com.yy.platform.component.starter.orm.util.Query;
import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.result.pager.PageResult;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/shiro/auth")
    @RequiresPermissions("a:b")
    public R shiroAuth(){
        log.info("into ShiroTestController shiroAuth");
        return R.Builder.success("ok").build();
    }

    @GetMapping("/search/page")
    public R<PageResult<GoodsEntity>> findBaseBoodsByCategoryId(@RequestParam(value = "current", defaultValue = "1") Long current,
                                                                @RequestParam(value = "size", defaultValue = "10") Long size,
                                                                @RequestParam("categoryId") Integer categoryId){

        if(null == categoryId || categoryId == 0){
            return R.Builder.badReq().build();
        }

        return R.Builder.success(goodsService.page(new Query<GoodsEntity>(current, size).getPage(), new LambdaQueryWrapper<GoodsEntity>()
                .eq(GoodsEntity::getCategoryId, categoryId)
                .eq(GoodsEntity::getEnable, CommonConstant.TABLE_DEFAULT_VALUE_ENABLE))
        ).build();
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
