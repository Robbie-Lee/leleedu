package com.lele.manager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.ClassInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;
import com.mysql.jdbc.StringUtils;

@Repository("classInfoDAO")
public class ClassInfoDAO extends MysqlBaseDAO<ClassInfo> {

	private final String HQL_ENTITY = "ClassInfo"; 
	
	public Pagination<ClassInfo> getClassInfoByPage(int curPage, int pageSize, 
							String classId, String className, String teacherName, 
							Date startDate, Date endDate, int scoreLevel) {
		StringBuilder hql = new StringBuilder();

		List<Object> values = new ArrayList<Object>();

		if (scoreLevel != 0) {
			hql.append("from " + HQL_ENTITY + " j join j.baseScoreLevel s where 1=1 ");
			
			hql.append(" and s.scoreLevel = ?" + values.size());
			values.add(startDate);
		}
		else
		{
			hql.append("from " + HQL_ENTITY + " j where 1=1 ");
		}
		
		if (!StringUtils.isNullOrEmpty(classId)) {
			hql.append(" and j.classId = ?" + values.size());
			values.add(classId);
		}
		if (!StringUtils.isNullOrEmpty(className)) {
			hql.append(" and j.className like ?" + values.size());
			values.add(className);
		}
		if (!StringUtils.isNullOrEmpty(teacherName)) {
			hql.append(" and j.teacherName = ?" + values.size());
			values.add(teacherName);
		}
		if (startDate != null) {
			hql.append(" and j.startDate = ?" + values.size());
			values.add(startDate);
		}
		if (endDate != null) {
			hql.append(" and j.endDate = ?" + values.size());
			values.add(startDate);
		}
		
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
}
