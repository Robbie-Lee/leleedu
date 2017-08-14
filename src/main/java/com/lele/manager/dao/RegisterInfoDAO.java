package com.lele.manager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;
import com.mysql.jdbc.StringUtils;

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
		this.executeHsqlWithoutEvict(hql, score, studentId, classId);
	}
	
	public Pagination<RegisterInfo> getRegisterInfoByPage(int curPage, int pageSize, 
					String classId, List<String> studentId, Date startDate, Date endDate) {

		StringBuilder hql = new StringBuilder();
		hql.append("from " + HQL_ENTITY + " where 1=1");

		List<Object> values = new ArrayList<Object>();

		if (!Strings.isNullOrEmpty(classId)) {
			hql.append(" and classId = ?" + values.size());
			values.add(classId);
		}
		
		if (startDate != null) {
			hql.append(" and registerDate >= ?" + values.size());
			values.add(startDate);
		}
		
		if (endDate != null) {
			hql.append(" and registerDate <= ?" + values.size());
			values.add(endDate);
		}
		
		if (studentId != null) {
			
			hql.append(" and studentId in (");
			
			for (int i = 0;i < studentId.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < studentId.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(studentId.get(i));
			}
		}

		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
}
