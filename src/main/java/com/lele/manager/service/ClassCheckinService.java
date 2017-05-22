package com.lele.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassCheckinDAO;
import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.entity.ClassCheckin;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("classCheckinService")
public class ClassCheckinService {

	@Autowired
	ClassCheckinDAO classCheckinDao;

	@Autowired
	ClassInfoDAO classInfoDao;

	public Pagination<ClassInfo> getClassAttendByPage(int curPage, int pageSize,
			String classId, String className, String teacherName, Date startDate, Date endDate) {

		return classInfoDao.getClassInfoByPage(curPage, pageSize, classId, 
								className, teacherName, startDate, endDate, 0);
	}
	
	public void checkin(String classId) {
		
		Date date = new Date();
		
		ClassCheckin classCheckin = classCheckinDao.getCheckIn(classId, date);
		
		if (classCheckin == null) {
			classCheckin = new ClassCheckin();
			
			classCheckin.setClassId(classId);
			classCheckin.setCheckinDate(date);
			
			classCheckinDao.save(classCheckin);
			classInfoDao.checkin(classId);
		}
	}
}
