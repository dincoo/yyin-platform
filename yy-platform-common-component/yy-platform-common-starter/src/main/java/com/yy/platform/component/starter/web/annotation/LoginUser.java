package com.yy.platform.component.starter.web.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {

    /**
     * 是否查询SysUser对象的所有信息， true则通过rpc接口查询
     * @return
     */
    boolean isFull() default false;

    boolean isTest() default false;
}
