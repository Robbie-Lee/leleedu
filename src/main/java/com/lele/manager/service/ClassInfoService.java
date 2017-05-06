package com.lele.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.entity.ClassInfo;
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
}
