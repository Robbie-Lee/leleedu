package com.lele.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("classInfoService")
public class ClassInfoService {

	@Autowired
	ClassInfoDAO classInfoDao;
	
	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	public List<Map<String, Object>> getStudentByClassId(String classId) {
		
		List<Map<String, Object>> studentLMap = new ArrayList<Map<String, Object>>();
		
//		List<StudentInfo> studentList = new ArrayList<StudentInfo>();
		List<RegisterInfo> ris = registerInfoDao.getRegisterInfo(classInfoDao.getClassKeyIdById(classId));
		
		for (int i = 0;i < ris.size();i ++) {
			Object obj = (Object) ris.get(i);
			Object o[] = (Object[]) obj;
			RegisterInfo ri = (RegisterInfo) o[0];
			
			for (StudentInfo si : ri.getStudentInfos()) {
//				studentList.add(si);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("studentId", si.getStudentId());
				map.put("name", si.getName());
				map.put("sex", si.getSex());
				map.put("attendYear", si.getAttendYear());
				map.put("school", si.getSchool());
				map.put("guarder", si.getGuarder());
				map.put("guarderName", si.getGuarderName());
				map.put("guarderPhone", si.getGuarderPhone());
				map.put("classScore", ri.getClassScore());
				
				studentLMap.add(map);
			}
		}
		
		return studentLMap;
	}
	
	public Pagination<ClassInfo> getClassInfoByPage(int curPage, int pageSize, 
						String classId, String className, String teacherName, 
						Date startDate, Date endDate, int scoreLevel, int classGrade) {
		return classInfoDao.getClassInfoByPage(curPage, pageSize, classId, className, 
									teacherName, startDate, endDate, scoreLevel, classGrade);
	}
	
	public Pagination<ClassInfo> getClassInfoByTeacherId(int curPage, int pageSize, 
			String classId, String className, String teacherId, 
			Date startDate, Date endDate, int scoreLevel, int classGrade) {
		return classInfoDao.getClassInfoByTeacherId(curPage, pageSize, classId, className, 
						teacherId, startDate, endDate, scoreLevel, classGrade);	
	}

	public void saveClassInfo(String classId, String className, String classRoom, Date startDate, 
			Date endDate, String classTime, String teacherName, String teacherId, int classCount, int classPrice, 
			boolean acceptDiscount, String classDescription, ScoreLevel scoreLevel, int classGrade, int registerLimit) {
		
		ClassInfo classInfo = classInfoDao.getClassInfoById(classId);
		
		if (classInfo == null) {
			classInfo = new ClassInfo();
			classInfo.setClassId(classId);
			classInfo.setCheckinCount(0);
			classInfo.setRegisterCount(0);
		}
		
		classInfo.setAcceptDiscount(acceptDiscount);
		classInfo.setClassCount(classCount);
		classInfo.setClassDescription(classDescription);
		classInfo.setClassName(className);
		classInfo.setClassPrice(classPrice);
		classInfo.setClassRoom(classRoom);
		classInfo.setClassTime(classTime);
		classInfo.setEndDate(endDate);
		classInfo.setScoreLevel(scoreLevel);
		classInfo.setStartDate(startDate);
		classInfo.setTeacherName(teacherName);
		classInfo.setTeacherId(teacherId);
		classInfo.setClassGrade(classGrade);
		classInfo.setRegisterLimit(registerLimit);
		classInfo.setValid(true);
		
		classInfoDao.save(classInfo);
	}
	
	public void activeClass(String classId, boolean active) {
		classInfoDao.activeClass(classId, active);
	}
}