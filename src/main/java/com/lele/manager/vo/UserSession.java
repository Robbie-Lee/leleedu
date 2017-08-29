package com.lele.manager.vo;

import java.io.Serializable;

import com.lele.manager.sys.entity.User;

public class UserSession implements Serializable {

	private static final long serialVersionUID = -4560629174565693605L;

	private User user;
//	private long userId;
//	private String userAccount;
	
	private String curURI;
	
	public String getCurURI() {
		return curURI;
	}
	public void setCurURI(String curURI) {
		this.curURI = curURI;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
