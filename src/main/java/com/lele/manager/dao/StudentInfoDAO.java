package com.lele.manager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;
import com.mysql.jdbc.StringUtils;

@Repository("studentInfoDAO")
public class StudentInfoDAO extends MysqlBaseDAO<StudentInfo> {

	private final String HQL_ENTITY = "StudentInfo"; 

	public StudentInfo getStudentInfoByCode(String code) {
		final String hql = "from " + HQL_ENTITY + " where wechatCode = ?0";
		return this.doQueryUnique(hql, code);
	}

	public StudentInfo getStudentInfoById(String studentId) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0";
		return this.doQueryUnique(hql, studentId);
	}

	public void updateTotalFee(String studentId, int fee) {
		final String hql = "update " + HQL_ENTITY + " set totalFee = totalFee + ?0 where studentId = ?1";
		this.executeHsqlWithoutEvict(hql, fee, studentId);
	}

	public void updateScoreLevel(String studentId, int scoreIndex) {
		final String hql = "update " + HQL_ENTITY + " set scoreIndex = ?0 where studentId = ?1";
		this.executeHsqlWithoutEvict(hql, scoreIndex, studentId);
	}

	public Pagination<StudentInfo> getStudentInfoByPage(int curPage, int pageSize, 
			String studentId, String studentName, String sex, 
			int attendYear, String guarderName, String guarderPhone) {

		StringBuilder hql = new StringBuilder();

		List<Object> values = new ArrayList<Object>();
		
		hql.append("from " + HQL_ENTITY + " j where 1=1 ");
		
		if (!StringUtils.isNullOrEmpty(studentId)) {
			hql.append(" and j.studentId like ?" + values.size());
			values.add("%" + studentId + "%");
		}
		if (!StringUtils.isNullOrEmpty(studentName)) {
			hql.append(" and j.name like ?" + values.size());
			values.add("%" + studentName + "%");
		}
		if (!StringUtils.isNullOrEmpty(sex)) {
			hql.append(" and j.sex = ?" + values.size());
			values.add(sex);
		}
		if (attendYear > 2000) {
			hql.append(" and j.attendYear = ?" + values.size());
			values.add(attendYear);
		}
		if (!StringUtils.isNullOrEmpty(guarderName)) {
			hql.append(" and j.guarderName like ?" + values.size());
			values.add("%" + guarderName + "%");
		}
		if (!StringUtils.isNullOrEmpty(guarderPhone)) {
			hql.append(" and j.guarderPhone like ?" + values.size());
			values.add("%" + guarderPhone + "%");
		}
		
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
}
