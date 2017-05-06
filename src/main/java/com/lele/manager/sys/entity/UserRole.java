package com.lele.manager.sys.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 3726677168978398503L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
    @ManyToOne(cascade = {CascadeType.ALL})  
    @JoinColumn(name="userid")  
	private User user;

    @ManyToOne(cascade = {CascadeType.ALL})  
    @JoinColumn(name="roleid")  
	private Role role;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
