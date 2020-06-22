package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author jaden
 * @since 2018-05-03
 */
@TableName("SYS_ROLE")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

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
    /**
     * 生效 1:生效  2: 不生效
     */
	@TableField("ENABLE_")
	private Integer enable;
    /**
     * 角色id
     */
    @TableId("ID_")
	private String id;
    /**
     * 角色名称
     */
	@TableField("ROLE_NAME")
	@NotBlank(message = "角色名不能为空")
	@Size(min = 2, max = 10, message = "角色名称长度在 {min} 到 {max} 个字符")
	private String roleName;
    /**
     * 备注
     */
	@TableField("REMARK_")
	private String remark;
    /**
     * 部门表id
     */
	@TableField("DEPT_ID")
	private String deptId;
	
	@TableField(exist=false)
	private String deptName;
    /**
     * 创建时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("CREATE_TIME")
	private Date createTime;
	
	@TableField(exist=false)
	private List<String> deptIdList;
	
	@TableField(exist=false)
	private List<String> menuIdList;

	@TableField(exist=false)
	private List<String> apiPermIdList;

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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public List<String> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<String> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public List<String> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		this.menuIdList = menuIdList;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<String> getApiPermIdList() {
		return apiPermIdList;
	}

	public void setApiPermIdList(List<String> apiPermIdList) {
		this.apiPermIdList = apiPermIdList;
	}

	@Override
	public String toString() {
		return "SysRole{" +
				"createBy='" + createBy + '\'' +
				", updateTime=" + updateTime +
				", updateBy='" + updateBy + '\'' +
				", enable=" + enable +
				", id='" + id + '\'' +
				", roleName='" + roleName + '\'' +
				", remark='" + remark + '\'' +
				", deptId='" + deptId + '\'' +
				", deptName='" + deptName + '\'' +
				", createTime=" + createTime +
				", deptIdList=" + deptIdList +
				", menuIdList=" + menuIdList +
				", apiPermIdList=" + apiPermIdList +
				'}';
	}
}
