package com.lele.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.TeacherInfoDAO;
import com.lele.manager.entity.TeacherInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("teacherInfoService")
public class TeacherInfoService {

	@Autowired
	TeacherInfoDAO teacherInfoDao;
	
	public TeacherInfo getTeacherInfoByKeyId(String teacherId) {
		return teacherInfoDao.getTeacherInfoById(teacherId);
	}
	
	public Pagination<TeacherInfo> getTeacherInfoByPage(int curPage, int pageSize, 
			String teacherId, String teacherName, String sex, 
			String phone, int status) {
		return teacherInfoDao.getTeacherInfoByPage(curPage, pageSize, teacherId, 
				teacherName, sex, phone, status);
	}
	
	public List<TeacherInfo> getTeacherInfoList() {
		return teacherInfoDao.findAll();
	}
	
	public void saveTeacherInfo(String teacherId, String name, String sex, 
			String phone, int status, Date birthDay, Date checkInTime, float classFeeRate,
			String college, String degree, String major, int minClassFee, int teachAge) {
		
		TeacherInfo teacherInfo = teacherInfoDao.getTeacherInfoById(teacherId);
				
		if (teacherInfo == null) {
			teacherInfo = new TeacherInfo();
			teacherInfo.setTeacherId(teacherId);
		}
		
		teacherInfo.setBirthDay(birthDay);
		teacherInfo.setCheckInTime(checkInTime);
		teacherInfo.setClassFeeRate(classFeeRate);
		teacherInfo.setCollege(college);
		teacherInfo.setDegree(degree);
		teacherInfo.setMajor(major);
		teacherInfo.setMinClassFee(minClassFee);
		teacherInfo.setName(name);
		teacherInfo.setPhone(phone);
		teacherInfo.setSex(sex);
		teacherInfo.setStatus(status);
		teacherInfo.setTeachAge(teachAge);
		
		teacherInfoDao.save(teacherInfo);
	}
}
