package com.lele.manager.sys.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
public class Resource extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5970300746411155751L;

	private String menuUrl;
	
	private String action;
	
	private int resType;  
	
	private String description;

//	@ManyToMany(targetEntity=ResourceRole.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//	@JoinTable(name = "leap_resource_role", joinColumns = { @JoinColumn(name = "resourceid", nullable = false, updatable = false) }, 
//			inverseJoinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) })
	
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getResType() {
		return resType;
	}

	public void setResType(int resType) {
		this.resType = resType;
	}
}
