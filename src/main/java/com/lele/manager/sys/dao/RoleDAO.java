package com.lele.manager.sys.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.entity.Role;

@Repository("roleDAO")
public class RoleDAO extends MysqlBaseDAO<Role> {

	private static final String HQL_ENTITY = "Role";
	
	public void activeRole(long roleId, boolean active) {
		
		final String hql = "update " + HQL_ENTITY + " set enable = ?0, modifyTime = ?1 where id = ?2";
		this.executeHsqlWithoutEvict(hql, active, new Date(), roleId);
	}
	
	public Role getRoleByName(String roleName) {
		
		final String hql = "from " + HQL_ENTITY + " where name = ?0";
		Role role = this.doQueryUnique(hql, roleName);
		
		return role;
	}
}
