package com.lele.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.ScoreLevelDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.EnrollInfo;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.service.DiscountService;
import com.lele.manager.sys.dao.Pagination;

@Service("wechatService")
public class WechatService {

	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	@Autowired
	ClassInfoDAO classInfoDao;
	
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	ScoreLevelDAO scoreLevelDao;

	public int enroll(String classId, String studentId, int fee, int payMode) {
		
		RegisterInfo ri = registerInfoDao.getRegisterInfo(classId, studentId);
		
		if (ri != null) {
			return -1;		//	 
		}
		
		ClassInfo classInfo = classInfoDao.getClassInfoById(classId);
		
		if (classInfo == null) {
			return -5;		// 
		}
		
		if (!classInfo.isValid()) {
			return -6;
		}
		
		if (classInfo.getRegisterLimit() <= classInfo.getRegisterCount()) {
			return -2;		// 	
		}
		
		StudentInfo studentInfo = studentInfoDao.getStudentInfoById(studentId);
		
		if (studentInfo == null) {
			return -4;      //
		}
		
		if (studentInfo.getScoreLevel().getScoreIndex() < classInfo.getScoreLevel().getScoreIndex()) {
			return -3;		// 
		}

		//
		if (payMode != 0) {
			ri = new RegisterInfo();
			ri.setClassId(classId);
			ri.setRegisterDate(new Date());
			ri.setRegisterFee(fee);
			ri.setStudentId(studentId);
			ri.setClassScore(0);
			ri.setRegisterMode(payMode);
			
			registerInfoDao.save(ri);
			classInfoDao.enroll(classId);
			studentInfoDao.updateTotalFee(studentId, fee);
		}
		
		return 0;
	}
	
	public int getDisCountFee(String classId, String studentId) {
		
		ClassInfo cInfo = classInfoDao.getClassInfoById(classId);
		
		if (!cInfo.isAcceptDiscount()) {
			return cInfo.getClassPrice();
		}
		
		StudentInfo sInfo = studentInfoDao.getStudentInfoById(studentId);
		return (int)(cInfo.getClassPrice() * discountService.getDiscountRate(sInfo.getTotalFee()));
	}
	
	public Pagination<EnrollInfo> getEnrollInfoByIds(int curPage, int pageSize, String studentId) {
		
		Pagination<RegisterInfo> ris = registerInfoDao.getStudentRegisterInfo(curPage, pageSize, studentId);
		
		Map<String, Integer> classScoreMap = new HashMap<String, Integer>();
		List<String> classIds = new ArrayList<String>();
		for (RegisterInfo ri : ris.getElements()) {
			classIds.add(ri.getClassId());
			classScoreMap.put(ri.getClassId(), ri.getClassScore());
		}
		
		EnrollInfo ei = new EnrollInfo();
		
		List<ClassInfo> cis = classInfoDao.getClassInfoByIds(classIds);
		for (ClassInfo ci : cis) {
			ei.addEnrollClass(ci.getClassId(), ci.getClassName(), ci.getTeacherName(), 
							ci.getClassCount(), ci.getCheckinCount(), classScoreMap.get(ci.getClassId()));
		}
		
		StudentInfo si = studentInfoDao.getStudentInfoById(studentId);
		
		Pagination<EnrollInfo> pci = new Pagination<EnrollInfo>();
		pci.setPageNumber(ris.getPageNumber());
		pci.setPageSize(ris.getPageSize());
		pci.setTotalElements(ris.getTotalElements());

		if (si != null) {
			ei.setScoreLevel(si.getScoreLevel());
			pci.setElements(ei);
		}
		return pci;
	}
}
