package com.lele.manager.enums;

public enum LoginStatus {

	USER_NOT_EXIST("user not exit"), PASSWORD_ERROR("password error"), USER_DISABLED("user disabled"), 
	LOGIN_SUCCESS("login success"), AUTO_LOGIN_FAILED("auto login failed");
	
	private String status;
	
	private LoginStatus(String status) {
		this.status = status;
	}
	
	public String status() {
		return this.status;
	}
}
