package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单管理表
 * </p>
 *
 * @author jaden
 * @since 2018-05-03
 */
@TableName("SYS_MENU")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

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
    /**
     * 生效 1:生效  2: 不生效
     */
	@TableField("ENABLE_")
	private Long enable;
    /**
     * 菜单表id
     */
    @TableId("ID_")
	private String id;
    /**
     * 父菜单ID，一级菜单为0
     */
	@TableField("PARENT_ID")
	private String parentId;
	
	@TableField(exist=false)
	private String parentName;
    /**
     * 菜单名称
     */
	@TableField("NAME")
	private String name;
    /**
     * 菜单URL
     */
	@TableField("URI")
	private String uri;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
	@TableField("PERMS")
	private String perms;
    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
	@TableField("TYPE")
	private Integer type;
    /**
     * 菜单图标
     */
	@TableField("ICON")
	private String icon;
    /**
     * 排序
     */
	@TableField("ORDER_NUM")
	private Long orderNum;
    /**
     * 创建时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;
	
	@TableField(exist=false)
	private List<SysMenu> subSysMenus;

	@TableField(exist=false)
	private List<SysApiPerm> sysApiPerms;


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

	public Long getEnable() {
		return enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<SysMenu> getSubSysMenus() {
		return subSysMenus;
	}

	public void setSubSysMenus(List<SysMenu> subSysMenus) {
		this.subSysMenus = subSysMenus;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<SysApiPerm> getSysApiPerms() {
		return sysApiPerms;
	}

	public void setSysApiPerms(List<SysApiPerm> sysApiPerms) {
		this.sysApiPerms = sysApiPerms;
	}

	@Override
	public String toString() {
		return "Menu{" +
			"createBy=" + createBy +
			", updateTime=" + updateTime +
			", updateBy=" + updateBy +
			", remark=" + remark +
			", enable=" + enable +
			", id=" + id +
			", parentId=" + parentId +
			", name=" + name +
			", uri=" + uri +
			", perms=" + perms +
			", type=" + type +
			", icon=" + icon +
			", orderNum=" + orderNum +
			", createTime=" + createTime +
			"}";
	}
}
