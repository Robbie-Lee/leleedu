package com.lele.manager.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

@Entity
@Table(name = "login_persistent")
public class UserCookie implements Serializable {

	private static final long serialVersionUID = 6747129681628462007L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name = "account")
	private String userName;

	@Column(name = "token")
	private String userToken;
	
	private Date lastLoginTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        final UserCookie that = (UserCookie) obj;
        
        if (this.userName.equals(that.userName)
        		&& this.lastLoginTime.equals(that.lastLoginTime) && this.userToken.equals(that.userToken)) {
        	return true;
        }

        return false;
    }	
    
    @Override 
    public int hashCode() {
    	return (this.userName + this.userToken).hashCode();
    }
}
