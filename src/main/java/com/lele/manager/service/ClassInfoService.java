package com.lele.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.sys.dao.Pagination;

@Service("classInfoService")
public class ClassInfoService {

	@Autowired
	ClassInfoDAO classInfoDao;
	
	public Pagination<ClassInfo> getClassInfoByPage(int curPage, int pageSize, 
						String classId, String className, String teacherName, 
						Date startDate, Date endDate, int scoreLevel) {
		return classInfoDao.getClassInfoByPage(curPage, pageSize, classId, className, 
									teacherName, startDate, endDate, scoreLevel);
	}

	public void saveClassInfo(String classId, String className, String classRoom, Date startDate, 
			Date endDate, String classTime, String teacherName, int classCount, int classPrice, 
			boolean acceptDiscount, String classDescription, ScoreLevel scoreLevel) {
		
		ClassInfo classInfo = classInfoDao.getClassInfoById(classId);
		
		if (classInfo == null) {
			classInfo = new ClassInfo();
			classInfo.setClassId(classId);
		}
		
		classInfo.setAcceptDiscount(acceptDiscount);
		classInfo.setClassCount(classCount);
		classInfo.setClassDescription(classDescription);
		classInfo.setClassName(className);
		classInfo.setClassPrice(classPrice);
		classInfo.setClassRoom(classRoom);
		classInfo.setClassTime(classTime);
		classInfo.setEndDate(endDate);
		classInfo.setRegisterCount(0);
		classInfo.setScoreLevel(scoreLevel);
		classInfo.setStartDate(startDate);
		classInfo.setTeacherName(teacherName);
		
		classInfoDao.save(classInfo);
	}	
}
