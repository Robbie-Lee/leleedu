package com.lele.manager.vo;

import java.io.Serializable;

public class UserSession implements Serializable {

	private static final long serialVersionUID = -4560629174565693605L;

	private long userId;
	private String userAccount;
	
	private String curURI;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getCurURI() {
		return curURI;
	}
	public void setCurURI(String curURI) {
		this.curURI = curURI;
	}
}
