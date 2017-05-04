package com.lele.manager.sys.dao;

import org.springframework.stereotype.Repository;

import com.lele.manager.dao.MysqlBaseDAO;
import com.lele.manager.entity.UserCookie;

@Repository("userCookieDAO")
public class UserCookieDAO extends MysqlBaseDAO<UserCookie> {

	private static final String HQL_ENTITY = "UserCookie";
	
	private static final String SQL_TABLE = "leap_test_login_persistent";

	public UserCookie getCookieByUserName(String userName) {
		final String hql = "from " + HQL_ENTITY + " where account = ?0";
		return this.doQueryUnique(UserCookie.class, hql, userName);
	}
	
	public void removeCookieByUserName(String userName) {
		final String sql = "delete from " + HQL_ENTITY + " where account = ?0";
		
		this.executeHsql(sql, userName);
	}
}
