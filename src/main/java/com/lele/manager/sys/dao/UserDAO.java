package com.lele.manager.sys.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.entity.User;
import com.lele.manager.utils.AES;

@Repository("userDAO")
public class UserDAO extends MysqlBaseDAO<User> {

	private static final String HQL_ENTITY = "User";
	
	public User getUserByName(String userName) {
		
		final String hql = "from " + HQL_ENTITY + " where account = ?0";
		User user = this.doQueryUnique(hql, userName);
		if (user != null) {
			user.setPassword(null);
		}
		
		return user;
	}
	
	public User getUserById(int userId) {
		final String hql = "from " + HQL_ENTITY + " where id = ?0";
		User user = this.doQueryUnique(hql, userId);
		if (user != null) {
			user.setPassword(null);
		}
		
		return user;	
	}
	
	public boolean verifyPassword(long userId, String account, String inputPassword) {
		
		final String hql = "select account from " + HQL_ENTITY + " where id = ?0 and password = ?1 and enable = ?2";
		String ac = this.doQueryUnique(String.class, hql, userId, AES.AESEncrypt(inputPassword), true);
		
		if (Strings.isNullOrEmpty(ac)) {
			return false;
		}
		
		if (ac.equals(account)) {
			return true;
		}
		
		return false;
	}
	
	public void changePassword(long userId, String inputPassword) {
		
		final String hql = "update " + HQL_ENTITY + " set password = ?0 where id = ?1";
		this.executeHsqlWithoutEvict(hql, inputPassword, userId);
	}
	
	public void activeUser(long userId, boolean active) {
		
		final String hql = "update " + HQL_ENTITY + " set enable = ?0, modifyTime = ?1 where id = ?2";
		this.executeHsqlWithoutEvict(hql, active, new Date(), userId);
	}
}
