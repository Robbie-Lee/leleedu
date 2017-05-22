package com.lele.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;

@Repository("registerInfoDAO")
public class RegisterInfoDAO extends MysqlBaseDAO<RegisterInfo> {

	private final String HQL_ENTITY = "RegisterInfo";
	
	public List<RegisterInfo> getRegisterInfo(String classId) {
		final String hql = "from " + HQL_ENTITY + " where classId = ?0";
		return this.doQueryList(hql, classId);
	}
	
	public long getRegisterCount(String classId) {
		String hql = "select count(*) from " + HQL_ENTITY + " where classId = ?0";
		return this.doQueryCount(hql, classId);
	}
}
