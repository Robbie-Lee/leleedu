package com.lele.manager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.lele.manager.entity.ClassAttend;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;

@Repository("classAttendDAO")
public class ClassAttendDAO extends MysqlBaseDAO<ClassAttend> {

	private final String HQL_ENTITY = "ClassAttend";

	public Pagination<ClassAttend> getClassAttendByPage(
			int curPage, int pageSize, String classId, String teacherId) {
		
		StringBuilder hql = new StringBuilder();

		List<Object> values = new ArrayList<Object>();

		if (!Strings.isNullOrEmpty(teacherId)) {
			hql.append("from " + HQL_ENTITY + " where teacherId = ?" + values.size());
			values.add(teacherId);
			
			if (!Strings.isNullOrEmpty(classId)) {
				hql.append(" and classId = ?" + values.size());
				values.add(classId);
			}
		}
		else {
			if (!Strings.isNullOrEmpty(classId)) {
				hql.append("from " + HQL_ENTITY + " where classId = ?" + values.size());
				values.add(classId);
			}
			else {
				hql.append("from " + HQL_ENTITY);
			}
		}
		
	
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
	
	public void checkIn(String classId) {

		String hql = "update " + HQL_ENTITY + " set attendCount = attendCount + 1 where classId = ?0";
		this.executeHsqlWithoutEvict(hql, classId);
	}
}
