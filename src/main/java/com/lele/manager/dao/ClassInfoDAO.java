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
	
	public String getClassIdByName(String className) {
		final String hql = "select classId from " + HQL_ENTITY + " where className = ?0";
		return this.doQueryUnique(String.class, hql, className);
	}
	
	public String getClassIdByStartDate(Date startDate) {
		final String hql = "select classId from " + HQL_ENTITY + " where startDate = ?0";
		return this.doQueryUnique(String.class, hql, startDate);
	}
	
	public String getClassIdByEndDate(Date endDate) {
		final String hql = "select classId from " + HQL_ENTITY + " where endDate = ?0";
		return this.doQueryUnique(String.class, hql, endDate);
	}

	public ClassInfo getClassInfoById(String classId) {
		final String hql = "from " + HQL_ENTITY + " where classId = ?0";
		return this.doQueryUnique(hql, classId);
	}
	
	public void activeClass(String classId, boolean active) {
		final String hql = "update " + HQL_ENTITY + " set valid = ?0 where classId = ?1";
		this.executeHsqlWithoutEvict(hql, active, classId);
		
	}
	
	public List<ClassInfo> getClassInfoByIds(List<String> classIds) {

		if (classIds.isEmpty()) {
			return new ArrayList<ClassInfo>();
		}

		StringBuilder hql = new StringBuilder();
		hql.append("from " + HQL_ENTITY + " where classId in (");

		for (int i = 0;i < classIds.size();i ++) {
			hql.append("?" + i);
			if (i < classIds.size() - 1) {
				hql.append(",");
			}
			else {
				hql.append(")");
			}
		}
		
		return this.doQueryList(hql.toString(), classIds.toArray());
	}
	
	public Pagination<ClassInfo> getClassInfoByPage(int curPage, int pageSize, 
							String classId, String className, String teacherName, 
							Date startDate, Date endDate, int scoreLevel, int classGrade) {
		
		StringBuilder hql = new StringBuilder();

		List<Object> values = new ArrayList<Object>();

		if (scoreLevel != 0) {
			hql.append("from " + HQL_ENTITY + " j join j.scoreLevel s where 1=1 ");
			
			hql.append(" and s.scoreIndex = ?" + values.size());
			values.add(scoreLevel);
		}
		else
		{
			hql.append("from " + HQL_ENTITY + " j where 1=1 ");
		}
		
		if (!StringUtils.isNullOrEmpty(classId)) {
			hql.append(" and j.classId like ?" + values.size());
			values.add("%" + classId + "%");
		}
		if (!StringUtils.isNullOrEmpty(className)) {
			hql.append(" and j.className like ?" + values.size());
			values.add("%" + className + "%");
		}
		if (!StringUtils.isNullOrEmpty(teacherName)) {
			hql.append(" and j.teacherName like ?" + values.size());
			values.add("%" + teacherName + "%");
		}
		if (startDate != null) {
			hql.append(" and j.startDate <= ?" + values.size());
			values.add(startDate);
		}
		if (endDate != null) {
			hql.append(" and j.endDate >= ?" + values.size());
			values.add(endDate);
		}
		if (classGrade > 0) {
			hql.append(" and j.classGrade = ?" + values.size());
			values.add(classGrade);
		}
		
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
	
	public void checkin(String classId) {
		String hql = "update " + HQL_ENTITY + " set checkinCount = checkinCount + 1 where classId = ?0";
		this.executeHsqlWithoutEvict(hql, classId);
	}
	
	public void enroll(String classId) {
		String hql = "update " + HQL_ENTITY + " set registerCount = registerCount + 1 where classId = ?0";
		this.executeHsqlWithoutEvict(hql, classId);
	}
}
