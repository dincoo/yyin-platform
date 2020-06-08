package com.yy.platform.component.starter.orm.configure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @Auther: zhanqingqi
 * @Date: 2019/12/16 16:22
 * @Description: 创建时间，更新时间填充
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime",new Date(System.currentTimeMillis()), metaObject);
        setFieldValByName("updateTime",new Date(System.currentTimeMillis()), metaObject);
        setFieldValByName("enable", 0, metaObject);
        setFieldValByName("version", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime",new Date(System.currentTimeMillis()), metaObject);
    }
}
