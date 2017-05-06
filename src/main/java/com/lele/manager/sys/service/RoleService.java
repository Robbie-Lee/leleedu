package com.lele.manager.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.sys.dao.RoleDAO;
import com.lele.manager.sys.entity.Role;

@Service("roleService")
public class RoleService {

	@Autowired
	RoleDAO roleDao;
	
	public Set<Role> getRoleSetByIds(String[] roleIds) {
		
		/*
		if (Strings.isNullOrEmpty(roleIds)) {
			return null;
		}

		String[] ids = roleIds.split(",");
		*/
		Set<Role> roleSet = new HashSet<Role>();
		for (String id : roleIds) {
			roleSet.add(roleDao.getById(Long.valueOf(id)));
		}
		
		return roleSet;
	}
	
	public List<Role> getRoleList() {
		return roleDao.findAll();
	}
	
	public void creatRole(Role role) {
		roleDao.save(role);
	}
	
	public void activeRole(long roleId, boolean active) {
		roleDao.activeRole(roleId, active);
	}
	
	public Role getRoleByName(String name) {
		return roleDao.getRoleByName(name);
	}
}
