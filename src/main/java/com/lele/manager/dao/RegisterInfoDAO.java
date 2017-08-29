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
	
/*	public Pagination<RegisterInfo> getStudentRegisterInfo(int curPage, int pageSize, String studentId) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0";
		return this.doQuery(hql, curPage, pageSize, studentId);
	}*/
	
	public void updateScore(String studentId, String classId, int score) {
		final String hql = "update " + HQL_ENTITY + " set classScore = ?0 where studentId = ?1 and classId = ?2"; 
		this.executeHsqlWithoutEvict(hql, score, studentId, classId);
	}
	
	public Pagination<RegisterInfo> getRegisterInfoById(int curPage, int pageSize, String studentKeyId) {
		final String hql = "from " + HQL_ENTITY + " as r left join r.studentInfos as sis "
				+ "where sis = ?0";
		return this.doQuery(hql, curPage, pageSize, studentKeyId);
	}
	
	public Pagination<RegisterInfo> getRegisterInfoByPage(int curPage, int pageSize, 
			List<String> classIds, List<String> studentIds, Date startDate, Date endDate) {

		StringBuilder hql = new StringBuilder();
		hql.append("from " + HQL_ENTITY + " as r ");
		
		List<Object> values = new ArrayList<Object>();
		
		if (classIds == null && studentIds == null) {
			hql.append(" where 1=1 ");
		}
		else if (classIds != null && studentIds == null) {
			hql.append(" left join r.classInfos as cis where cis.id in (");
			
			for (int i = 0;i < classIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < classIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(classIds.get(i));
			}
		}
		else if (classIds == null && studentIds != null) {
			hql.append(" left join r.studentInfos as sis where sis.id in (");
			
			for (int i = 0;i < studentIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < studentIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(studentIds.get(i));
			}
		}
		else {
			hql.append(" left join r.classInfos as cis left join r.studentInfos as sis where cis.id in (");

			for (int i = 0;i < classIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < classIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(classIds.get(i));
			}
			
			hql.append(" and sis.id in (");
			for (int i = 0;i < studentIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < studentIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(studentIds.get(i));
			}			
		}
		
		if (startDate != null) {
			hql.append(" and registerDate >= ?" + values.size());
			values.add(startDate);
		}
		
		if (endDate != null) {
			hql.append(" and registerDate <= ?" + values.size());
			values.add(endDate);
		}
		
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
}
