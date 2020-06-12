package com.yy.platform.system.management.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class SysLoginVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "用户名不能为空")
	private String userName;

	@NotBlank(message = "密码不能为空")
	private String passwd;

	@NotBlank(message = "验证码不能为空")
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	
}
