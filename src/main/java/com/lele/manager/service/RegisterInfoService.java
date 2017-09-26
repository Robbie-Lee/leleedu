package com.lele.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.DiscountDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.Discount;
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
	
	@Autowired
	DiscountDAO discountDao;

	public Pagination<Map> getRegisterInfoById(int curPage, int pageSize, String studentId) {
		long studentKeyId = studentInfoDao.getStudentKeyIdById(studentId);
		Pagination<Map> plmap = registerInfoDao.getRegisterInfoById(curPage, pageSize, studentKeyId);
		return plmap;
	}

	public Pagination<RegisterInfo> getRegisterInfoByPage(int curPage, int pageSize, 
			String className, String studentName, Date startDate, Date endDate) {
		
		List<Long> sidList = null;
		
		if (!Strings.isNullOrEmpty(studentName)) {
			sidList = studentInfoDao.getStudentKeyIdByName(studentName);
		}
		
		List<Long> cidList = null;
		
		if (!Strings.isNullOrEmpty(className)) {
			cidList = classInfoDao.getClassKeyIdByName(className);
		}
		
		Pagination<RegisterInfo> pri = registerInfoDao.getRegisterInfoByPage(curPage, pageSize, cidList, sidList, startDate, endDate);
		for (RegisterInfo ri : pri.getElements()) {
			for (ClassInfo ci : ri.getClassInfos()) {
				ci.setId(0);
				ci.setScoreLevel(null);
			}
			for (StudentInfo si : ri.getStudentInfos()) {
				si.setId(0);
				si.setScoreLevel(null);
				
				Discount discount = discountDao.getDiscount(si.getTotalFee());
				if (discount == null) {
					discount = new Discount();
					discount.setDiscountRate(1.0f);
					discount.setLowerFee(si.getTotalFee());
					discount.setUpperFee(si.getTotalFee());
				}
				si.setDiscountRate(discount.getDiscountRate());
			}
		}
		
		return pri;
	}
}
