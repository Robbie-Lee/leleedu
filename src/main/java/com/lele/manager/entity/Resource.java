package com.lele.manager.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "leap_test_resource")
public class Resource extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5970300746411155751L;

	private String resource;
	
	private String name;

//	@ManyToMany(targetEntity=ResourceRole.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//	@JoinTable(name = "leap_resource_role", joinColumns = { @JoinColumn(name = "resourceid", nullable = false, updatable = false) }, 
//			inverseJoinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) })
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
