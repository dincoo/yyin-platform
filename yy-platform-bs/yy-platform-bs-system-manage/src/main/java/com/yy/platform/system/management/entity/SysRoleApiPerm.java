package com.yy.platform.system.management.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色-部门-中间表
 * </p>
 *
 * @author jaden
 * @since 2018-05-03
 */
@TableName("SYS_ROLE_API_PERM")
public class SysRoleApiPerm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色部门表id
     */
    @TableId("ID_")
	private String id;
    /**
     * 角色表id
     */
	@TableField("ROLE_ID")
	private String roleId;
    /**
     * 部门表id
     */
	@TableField("API_PERM_ID")
	private String apiPermId;
    /**
     * 创建时间
     */
	@TableField("CREATE_TIME")
	private Date createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getApiPermId() {
		return apiPermId;
	}

	public void setApiPermId(String apiPermId) {
		this.apiPermId = apiPermId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SysRoleApiPerm{" +
				"id='" + id + '\'' +
				", roleId='" + roleId + '\'' +
				", apiPermId='" + apiPermId + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
