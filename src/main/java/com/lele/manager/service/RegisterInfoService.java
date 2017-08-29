package com.lele.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("registerService")
public class RegisterInfoService {

	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	@Autowired
	ClassInfoDAO classInfoDao;

	public Pagination<RegisterInfo> getRegisterInfoById(int curPage, int pageSize, String studentId) {
		String studentKeyId = studentInfoDao.getStudentKeyIdById(studentId);
		return registerInfoDao.getRegisterInfoById(curPage, pageSize, studentKeyId);
	}

	public Pagination<RegisterInfo> getRegisterInfoByPage(int curPage, int pageSize, 
			String className, String studentName, Date startDate, Date endDate) {
		
		List<String> sidList = null;
		
		if (!Strings.isNullOrEmpty(studentName)) {
			sidList = studentInfoDao.getStudentKeyIdByName(studentName);
		}
		
		List<String> cidList = null;
		
		if (!Strings.isNullOrEmpty(className)) {
			cidList = classInfoDao.getClassKeyIdByName(className);
		}
		
		return registerInfoDao.getRegisterInfoByPage(curPage, pageSize, cidList, sidList, startDate, endDate);
	}
}
