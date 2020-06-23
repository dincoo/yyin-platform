package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@TableName("SYS_API_PERM")
public class SysApiPerm implements Serializable {

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
    @NotNull(message = "接口名称不能为空")
    private String name;

    /**
     * 接口地址
     */
    @TableField("URI")
    @NotNull(message = "接口地址不能为空")
    private String uri;

    /**
     * 权限值
     */
    @TableField("PERM")
    @NotNull(message = "权限编号不能为空")
    private String perm;

    /**
     * 类型 - 系统接口1，业务接口2
     */
    @TableField("TYPE")
    @NotNull(message = "接口类型不能为空")
    private Integer type;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        return "SysApiPerm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", perm='" + perm + '\'' +
                ", type=" + type +
                ", orderNum=" + orderNum +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
