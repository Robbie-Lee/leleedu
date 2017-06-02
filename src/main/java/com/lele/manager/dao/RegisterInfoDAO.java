package com.lele.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;

@Repository("registerInfoDAO")
public class RegisterInfoDAO extends MysqlBaseDAO<RegisterInfo> {

	private final String HQL_ENTITY = "RegisterInfo";
	
	public List<RegisterInfo> getRegisterInfo(String classId) {
		final String hql = "from " + HQL_ENTITY + " where classId = ?0";
		return this.doQueryList(hql, classId);
	}
	
	public RegisterInfo getRegisterInfo(String classId, String studentId) {
		final String hql = "from " + HQL_ENTITY + " where classId = ?0 and studentId = ?1";
		return this.doQueryUnique(hql, classId, studentId);
	}
	
	public long getRegisterCount(String classId) {
		String hql = "select count(*) from " + HQL_ENTITY + " where classId = ?0";
		return this.doQueryCount(hql, classId);
	}
	
	public Pagination<RegisterInfo> getStudentRegisterInfo(int curPage, int pageSize, String studentId) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0";
		return this.doQuery(hql, curPage, pageSize, studentId);
	}
	
	public void updateScore(String studentId, String classId, int score) {
		final String hql = "update " + HQL_ENTITY + " set classScore = ?0 where studentId = ?1 and classId = ?2"; 
		this.executeHsqlWithoutEvict(hql, studentId, classId, score);
	}
}
