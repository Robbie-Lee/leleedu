package com.lele.manager.sys.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2315764686669845311L;

	private String name;
	
	private String description;
	
	private boolean enable;
	
//	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "role")
//	private Set<User> user;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "role_resource", joinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "resourceid", nullable = false, updatable = false) })
	private Set<Resource> resource;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
/*
	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
*/
	public Set<Resource> getResource() {
		return resource;
	}

	public void setResource(Set<Resource> resource) {
		this.resource = resource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
