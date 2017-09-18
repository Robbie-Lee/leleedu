package com.lele.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.DiscountDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("studentInfoService")
public class StudentInfoService {
	
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	@Autowired
	ClassInfoDAO classInfoDao;

	@Autowired
	DiscountDAO discountDao;

	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	public void updateStudentScoreLevel(String studentId, int scoreIndex) {
		studentInfoDao.updateScoreLevel(studentId, scoreIndex);
	}
	
	public void updateStudentScore(String classId, String studentIds, String classScores) {
		
		if (!Strings.isNullOrEmpty(studentIds) && !Strings.isNullOrEmpty(classScores)) {
			String[] sis = studentIds.split(",");
			String[] css = classScores.split(",");

			long classKeyId = classInfoDao.getClassKeyIdById(classId);
			
			if (css.length > 0 && sis.length > 0) {
				for (int i = 0;i < sis.length;i ++) {
					long studentKeyId = studentInfoDao.getStudentKeyIdById(sis[i]);
					registerInfoDao.updateScore(studentKeyId, 
							classKeyId, Integer.valueOf(css[i]));
				}
			}
/*			
			if (css.length > 0 && cis.length > 0) {
				for (int i = 0;i < cis.length;i ++) {
					registerInfoDao.updateScore(studentKeyId, 
							classInfoDao.getClassKeyIdById(cis[i]), Integer.valueOf(css[i]));
				}
			}
*/		}
	}
	
	public StudentInfo getStudentInfoByCode(String code) {
		return studentInfoDao.getStudentInfoByCode(code);
	}
	
	public Pagination<StudentInfo> getStudentInfoByPage(int curPage, int pageSize, 
			String studentId, String studentName, String sex, int attendYear,
			String guarderName, String guarderPhone) {
		return studentInfoDao.getStudentInfoByPage(curPage, pageSize, studentId, 
				studentName, sex, attendYear, guarderName, guarderPhone);
	}
	
	public boolean isStudentExist(String studentId) {
		
		StudentInfo studentInfo = studentInfoDao.getStudentInfoById(studentId);
		if (studentInfo == null) {
			return false;
		}
		return true;
	}
	
	public void saveStudentInfo(String studentId, String name, String sex, 
			int attendYear, int guarder, String guarderName, String guarderPhone, 
			String note, String school, ScoreLevel scoreLevel) {

		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];

		StudentInfo studentInfo = studentInfoDao.getStudentInfoById(studentId);
		
		if (studentInfo == null) {
			studentInfo = new StudentInfo();
			studentInfo.setStudentId(studentId);
			
			studentInfo.setDiscountRate(1.0f);
			studentInfo.setTotalFee(0);
		}

		if (idandcode.length == 2) {
			String code = idandcode[1];
			studentInfo.setWechatCode(code);
			System.out.println("register studentId: " + studentId + " and code: " + code);
		}
		
		studentInfo.setAttendYear(attendYear);
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
