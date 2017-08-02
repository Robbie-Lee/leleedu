package com.lele.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.sys.dao.Pagination;

@Service("classInfoService")
public class ClassInfoService {

	@Autowired
	ClassInfoDAO classInfoDao;
	
	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	public List<String> getStudentNameByClassId(String classId) {
		
		List<String> studentNameList = new ArrayList<String>();
		List<RegisterInfo> ris = registerInfoDao.getRegisterInfo(classId);
		
		for (RegisterInfo ri : ris) {
			studentNameList.add(studentInfoDao.getStudentInfoById(ri.getStudentId()).getName());
		}
		
		return studentNameList;
	}
	
	public Pagination<ClassInfo> getClassInfoByPage(int curPage, int pageSize, 
						String classId, String className, String teacherName, 
						Date startDate, Date endDate, int scoreLevel, int classGrade) {
		return classInfoDao.getClassInfoByPage(curPage, pageSize, classId, className, 
									teacherName, startDate, endDate, scoreLevel, classGrade);
	}
	
	public void saveClassInfo(String classId, String className, String classRoom, Date startDate, 
			Date endDate, String classTime, String teacherName, int classCount, int classPrice, 
			boolean acceptDiscount, String classDescription, ScoreLevel scoreLevel, int classGrade, int registerLimit) {
		
		ClassInfo classInfo = classInfoDao.getClassInfoById(classId);
		
		if (classInfo == null) {
			classInfo = new ClassInfo();
			classInfo.setClassId(classId);
			classInfo.setCheckinCount(0);
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
		classInfo.setClassGrade(classGrade);
		classInfo.setRegisterLimit(registerLimit);
		
		classInfoDao.save(classInfo);
	}
	
	public void activeClass(String classId, boolean active) {
		classInfoDao.activeClass(classId, active);
	}
}