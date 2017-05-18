package com.lele.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.DiscountDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("studentInfoService")
public class StudentInfoService {
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	@Autowired
	DiscountDAO discountDao;

	public Pagination<StudentInfo> getStudentInfoByPage(int curPage, int pageSize, 
			String studentId, String studentName, String sex, int grade,
			String guarderName, String guarderPhone) {
		return studentInfoDao.getStudentInfoByPage(curPage, pageSize, studentId, 
				studentName, sex, grade, guarderName, guarderPhone);
	}
	
	public void saveStudentInfo(String studentId, String name, String sex, 
			int grade, int guarder, String guarderName, String guarderPhone, 
			String note, String school, ScoreLevel scoreLevel) {
		
		StudentInfo studentInfo = studentInfoDao.getStudentInfoById(studentId);
		
		if (studentInfo == null) {
			studentInfo = new StudentInfo();
			studentInfo.setStudentId(studentId);
			
			studentInfo.setDiscountRate(1.0f);
			studentInfo.setTotalFee(0);
		}
		
		studentInfo.setGrade(grade);
		studentInfo.setGuarder(guarder);
		studentInfo.setGuarderName(guarderName);
		studentInfo.setGuarderPhone(guarderPhone);
		studentInfo.setName(name);
		studentInfo.setNote(note);
		studentInfo.setSchool(school);
		studentInfo.setScoreLevel(scoreLevel);
		studentInfo.setSex(sex);
		studentInfo.setStudentId(studentId);
		
		studentInfoDao.save(studentInfo);
	}
}
