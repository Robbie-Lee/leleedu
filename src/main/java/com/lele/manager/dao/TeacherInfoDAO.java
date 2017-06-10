package com.lele.manager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.TeacherInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;
import com.mysql.jdbc.StringUtils;

@Repository("teacherInfoDAO")
public class TeacherInfoDAO extends MysqlBaseDAO<TeacherInfo> {
	
	private final String HQL_ENTITY = "TeacherInfo"; 

	public String getTeacherIdByName(String teacherName) {
		final String hql = "select teacherId from " + HQL_ENTITY + " where name = ?0";
		return this.doQueryUnique(String.class, hql, teacherName);
	}
	
	public TeacherInfo getTeacherInfoById(String teacherId) {
		final String hql = "from " + HQL_ENTITY + " where teacherId = ?0";
		return this.doQueryUnique(hql, teacherId);
	}

	public TeacherInfo getTeacherInfoByName(String teacherName) {
		final String hql = "from " + HQL_ENTITY + " where name = ?0";
		return this.doQueryUnique(hql, teacherName);
	}

	public Pagination<TeacherInfo> getTeacherInfoByPage(int curPage, int pageSize, 
			String teacherId, String teacherName, String sex, 
			String phone, int status) {

		StringBuilder hql = new StringBuilder();

		List<Object> values = new ArrayList<Object>();
		
		hql.append("from " + HQL_ENTITY + " j where 1=1 ");
		
		if (!StringUtils.isNullOrEmpty(teacherId)) {
			hql.append(" and j.teacherId like ?" + values.size());
			values.add("%" + teacherId + "%");
		}
		if (!StringUtils.isNullOrEmpty(teacherName)) {
			hql.append(" and j.name like ?" + values.size());
			values.add("%" + teacherName + "%");
		}
		if (!StringUtils.isNullOrEmpty(sex)) {
			hql.append(" and j.sex = ?" + values.size());
			values.add(sex);
		}
		if (!StringUtils.isNullOrEmpty(phone)) {
			hql.append(" and j.phone like ?" + values.size());
			values.add("%" + phone + "%");
		}
		if (status >= 0) {
			hql.append(" and j.status = ?" + values.size());
			values.add(status);
		}
		
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
}