package com.lele.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.lele.manager.dao.ClassAttendDAO;
import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.TeacherInfoDAO;
import com.lele.manager.entity.ClassAttend;
import com.lele.manager.sys.dao.Pagination;

@Service("classAttendService")
public class ClassAttendService {

	@Autowired
	ClassAttendDAO classAttendDao;
	
	@Autowired
	ClassInfoDAO classInfoDao;
	
	@Autowired
	TeacherInfoDAO teacherInfoDao;
	
	public Pagination<ClassAttend> getClassAttendByPage(int curPage, int pageSize,
			String classId, String className, String teacherName, Date startDate, Date endDate) {

		if (Strings.isNullOrEmpty(classId)) {
			if (!Strings.isNullOrEmpty(className)) {
				classId = classInfoDao.getClassIdByName(className);
			}
			else {
				if (startDate == null) {
					if (endDate != null) {
						classId = classInfoDao.getClassIdByEndDate(endDate);
					}
				}
				else {
					classId = classInfoDao.getClassIdByStartDate(startDate);
				}
			}
		}
		
		String teacherId = "";
		if (!Strings.isNullOrEmpty(teacherName)) {
			teacherId = teacherInfoDao.getTeacherIdByName(teacherName);
		}

		return classAttendDao.getClassAttendByPage(curPage, pageSize, classId, teacherId);
	}
	
	public void checkIn(String classId) {
		classAttendDao.checkIn(classId);
	}
}
