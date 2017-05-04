package com.lele.manager.entity;

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
@Table(name = "leap_role_resource")
public class RoleResoure implements Serializable {

	private static final long serialVersionUID = 3717309962771607701L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
    @ManyToOne(cascade = {CascadeType.ALL})  
    @JoinColumn(name="roleid")  
	private Role role;

    @ManyToOne(cascade = {CascadeType.ALL})  
    @JoinColumn(name="resourceid")  
	private Resource resource;

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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
