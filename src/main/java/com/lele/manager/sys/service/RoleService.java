package com.lele.manager.sys.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.sys.dao.RoleDAO;
import com.lele.manager.sys.entity.Role;

@Service("roleService")
public class RoleService {

	@Autowired
	RoleDAO roleDao;
	
	private static final String HQL_ENTITY = "Role";

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
		List<Role> roleList = roleDao.findAll();
		for (Role role : roleList) {
			role.setResource(null);
		}
		return roleList;
	}
	
	public void saveRole(Role role) {
		
		Role roleInDB = getRoleByName(role.getName());
		if (roleInDB == null) {
			roleDao.save(role);
		}
		
		roleInDB.setEnable(role.getEnable());
		roleInDB.setDescription(role.getDescription());
		roleInDB.setResource(role.getResource());
		roleInDB.setModifyTime(new Date());
		
		roleDao.save(roleInDB);
	}
	
	public void activeRole(long roleId, boolean active) {
		final String hql = "update " + HQL_ENTITY + " set enable = ?0, modifyTime = ?1 where id = ?2";
		roleDao.executeHsqlWithoutEvict(hql, active, new Date(), roleId);
	}
	
	public Role getRoleByName(String name) {
		final String hql = "from " + HQL_ENTITY + " where name = ?0";
		return roleDao.doQueryUnique(hql, name);
	}
	
	public Pagination<Role> getRoleByPage(int curPage, int pageSize) {
		final String hql = "from " + HQL_ENTITY;
		return roleDao.doQuery(hql, curPage, pageSize);
	}
}
