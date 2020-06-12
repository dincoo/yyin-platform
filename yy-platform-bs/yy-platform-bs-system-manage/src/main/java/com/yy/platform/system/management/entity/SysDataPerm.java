package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("SYS_DATA_PERM")
public class SysDataPerm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @TableId("ID_")
    private String id;

    /**
     * 部门名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 接口地址
     */
    @TableField("NUMBER")
    private String number;

    /**
     * 权限值
     */
    @TableField("FIELD")
    private String field;

    /**
     * 类型 - 系统接口1，业务接口2
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * 类型 - 系统接口1，业务接口2
     */
    @TableField("CLASS_PATH")
    private String classPath;

    /**
     * 类型 - 系统接口1，业务接口2
     */
    @TableField("RULE_VALUE")
    private String ruleValue;

    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    private Long orderNum;
    /**
     * 生效 1:生效  2: 不生效
     */
    @TableField("ENABLE_")
    private Integer enable;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("CREATE_BY")
    private String createBy;
    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;
    /**
     * 更新人
     */
    @TableField("UPDATE_BY")
    private String updateBy;
    /**
     * 备注
     */
    @TableField("REMARK_")
    private String remark;

    @TableField(exist=false)
    private String menuId;

    @TableField(exist=false)
    private String roleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysDataPerm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", field='" + field + '\'' +
                ", type=" + type +
                ", classPath='" + classPath + '\'' +
                ", ruleValue='" + ruleValue + '\'' +
                ", orderNum=" + orderNum +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", remark='" + remark + '\'' +
                ", menuId='" + menuId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
