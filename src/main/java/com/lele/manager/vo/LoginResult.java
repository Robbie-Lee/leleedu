package com.lele.manager.vo;

import java.io.Serializable;

import com.lele.manager.sys.entity.User;

public class LoginResult implements Serializable {

	private static final long serialVersionUID = -1193204165364345117L;

	private User user;
	
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

	public int getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(int resultFlag) {
		this.resultFlag = resultFlag;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
