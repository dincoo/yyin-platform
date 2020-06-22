package com.yy.platform.system.management.utils;

import com.xiaoleilu.hutool.date.DateUtil;

/**
 * @description String的工具类
 * @author yezhaoxing
 * @since 2018/06/05
 */
public class ToolForID {

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成系统日志切面表id工具
     */
    public static String getSysLogID() {
        String now = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
        String id = "SL" + now + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成系统配置信息表id工具
     */
    public static String getSysConfigID() {
        String id = "SC_" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成利率配置表id工具
     */
    public static String getSysRateConfigureID() {
        String id = "rc_" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成部门表id工具
     */
    public static String getSysDeptID() {
        String id = "dp" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成角色与部门对应关系表id工具
     */
    public static String getSysRoleDeptID() {
        String id = OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成角色与菜单对应关系表id工具
     */
    public static String getSysRoleMenuID() {
        String id = OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成角色表id工具
     */
    public static String getSysRoleID() {
        String id = "SR_" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成用户与角色对应关系表id工具
     */
    public static String getSysUserRoleID() {
        String id = OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author liuzy
     * @since 2018/6/12 
     * @description 生成角色表id工具
     */
    public static String getSysUserID() {
        String id = "SU_" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     *
     */
    public static String getSysApiPermID() {
        String id = "SAP_" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     *
     */
    public static String getSysMenuApiPermID() {
        String id = "MAP_" + OnlyCodeGenerator.distriId();
        return id;
    }

    /**
     * @author zhanlingxia
     * @since 2018/6/14 
     * @description 生成模型报告
     */
    public static String getModelID() {
        String id = "MI" + OnlyCodeGenerator.distriId();
        return id;
    }
    
    public static String getRuleID() {
        String id = "RI" + OnlyCodeGenerator.distriId();
        return id;
    }
    public static String getBusinessID() {
        String id = "BI" + OnlyCodeGenerator.distriId();
        return id;
    }
    public static String getAmountLimitID() {
        String id = "BA" + OnlyCodeGenerator.distriId();
        return id;
    }

    public static String getSysRoleApiPermId() {
        String id = "RAP_" + OnlyCodeGenerator.distriId();
        return id;
    }
}
