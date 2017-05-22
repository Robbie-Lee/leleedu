package com.lele.manager.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.ClassCheckin;
import com.lele.manager.sys.dao.MysqlBaseDAO;

@Repository("classAttendDAO")
public class ClassCheckinDAO extends MysqlBaseDAO<ClassCheckin>{

	private final String HQL_ENTITY = "ClassCheckin";
	
	public ClassCheckin getCheckIn(String classId, Date date) {
		final String hql = "from " + HQL_ENTITY + " where classId = ?0 and checkinDate = ?1";
		return this.doQueryUnique(hql, classId, date);
	}
	
	public long getCheckInCount(String classId, Date startDate, Date endDate) {
		final String hql = "select count(*) from " + HQL_ENTITY + 
				" where classId = ?0 and checkinDate between ?1 and ?2";
		return this.doQueryCount(hql, classId, startDate, endDate);
	}
}
