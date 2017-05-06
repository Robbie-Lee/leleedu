package com.lele.manager.sys.dao;

import org.springframework.stereotype.Repository;

import com.lele.manager.sys.entity.UserCookie;

@Repository("userCookieDAO")
public class UserCookieDAO extends MysqlBaseDAO<UserCookie> {

	private static final String HQL_ENTITY = "UserCookie";
	
	private static final String SQL_TABLE = "login_persistent";

	public UserCookie getCookieByUserName(String userName) {
		final String hql = "from " + HQL_ENTITY + " where account = ?0";
		return this.doQueryUnique(UserCookie.class, hql, userName);
	}
	
	public void removeCookieByUserName(String userName) {
		final String sql = "delete from " + HQL_ENTITY + " where account = ?0";
		
		this.executeHsql(sql, userName);
	}
}
