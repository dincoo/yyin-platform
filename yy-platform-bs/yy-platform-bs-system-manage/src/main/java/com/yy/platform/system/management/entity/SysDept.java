package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 部门管理表
 * </p>
 *
 * @author jaden
 * @since 2018-05-03
 */
@TableName("SYS_DEPT")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

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
    /**
     * 部门id
     */
    @TableId("ID_")
	private String id;
    /**
     * 上级部门ID，一级部门为0
     */
	@TableField("PARENT_ID")
	private String parentId;
	
	@TableField(exist=false)
	private String parentName;
    /**
     * 部门名称
     */
	@TableField("NAME")
	private String name;
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

	
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "Dept{" +
			"createTime=" + createTime +
			", createBy=" + createBy +
			", updateTime=" + updateTime +
			", updateBy=" + updateBy +
			", remark=" + remark +
			", id=" + id +
			", parentId=" + parentId +
			", name=" + name +
			", orderNum=" + orderNum +
			", enable=" + enable +
			"}";
	}
}
