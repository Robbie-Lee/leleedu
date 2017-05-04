package com.lele.manager.vo;

import java.io.Serializable;

public class LoginResult implements Serializable {

	private static final long serialVersionUID = -1193204165364345117L;

	private long userId;
	private String userName;
	
	private String loginStatus;
	private String authUrl;
	
	private int resultFlag;
	
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(int resultFlag) {
		this.resultFlag = resultFlag;
	}
}
