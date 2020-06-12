package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jaden
 * @since 2018-05-03
 */
@TableName("SYS_USER")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆名称
     */
    @TableField("LOGIN_NAME")
    private String loginName;
    /**
     * 最近登录时间
     */
    @TableField("LAST_LOGIN_TIME")
    private Date lastLoginTime;
    /**
     * 最近登陆ip
     */
    @TableField("LAST_LOGIN_IP")
    private String lastLoginIp;
    /**
     * 用户id
     */
    @TableId("ID_")
    private String id;
    /**
     * 部门id
     */
    @TableField("DEPT_ID")
    private String deptId;

    @TableField(exist = false)
    private String deptName;

    /**
     * 用户名称
     */
    @TableField("USERNAME")
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 用户密码
     */
    @TableField("PASSWD")
    @NotBlank(message = "用户密码不能为空")
    private String passwd;
    /**
     * 用户手机号
     */
    @TableField("MOBILE")
    private String mobile;
    /**
     * 用户邮箱
     */
    @TableField("EMAIL")
    private String email;
    /**
     * 状态 0：禁用 1：正常
     */
    @TableField("STATUS")
    @NotNull(message = "状态不能为空")
    private Integer status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("UPDATE_TIME")
    private Date updateTime;
    /**
     * 更新人
     */
    @TableField("UPDATE_BY")
    private String updateBy;

    @TableField(exist = false)
    @NotEmpty(message = "请至少选择一个角色")
    private List<String> roleIdList;

    @TableField(exist = false)
    private String secretKey;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "User{" + "loginName=" + loginName + ", lastLoginTime=" + lastLoginTime + ", lastLoginIp=" + lastLoginIp
                + ", id=" + id + ", deptId=" + deptId + ", username=" + username + ", passwd=" + passwd + ", mobile="
                + mobile + ", email=" + email + ", status=" + status + ", createTime=" + createTime + ", createBy="
                + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + "}";
    }

}
