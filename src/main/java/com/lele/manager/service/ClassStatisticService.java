package com.lele.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassCheckinDAO;
import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.TeacherInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.ClassStatistic;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.entity.TeacherInfo;
import com.lele.manager.sys.dao.Pagination;

@Service("classStatisticService")
public class ClassStatisticService {

	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	@Autowired
	ClassInfoDAO classInfoDao;
	
	@Autowired
	TeacherInfoDAO teacherInfoDao;
	
	@Autowired
	ClassCheckinDAO classCheckinDao;
	
	public Pagination<ClassStatistic> calcTeacherSalary(int curPage, int pageSize,
					String teacherName, Date startDate, Date endDate) {
		
		Pagination<ClassInfo> classInfoPage = classInfoDao.getClassInfoByPage(
				curPage, pageSize, "", "", teacherName, startDate, endDate, 0, 0);
		
		List<ClassStatistic> csList = new ArrayList<ClassStatistic>();
		for (ClassInfo ci : classInfoPage.getElements()) {
			
			List<RegisterInfo> registerInfoList = registerInfoDao.getRegisterInfo(ci.getClassId());
			TeacherInfo teacherInfo = teacherInfoDao.getTeacherInfoByName(ci.getTeacherName());
			
			Date sDate = null;
			if (startDate == null) {
				sDate = ci.getStartDate();
			}
			else {
				sDate = startDate;
			}
			
			Date eDate = null;
			if (endDate == null) {
				eDate = ci.getEndDate();
			}
			else {
				eDate = endDate;
			}
			
			long checkinCount = classCheckinDao.getCheckInCount(ci.getClassId(), sDate, eDate);
			
			ClassStatistic cs = new ClassStatistic();
			cs.setAttendCount((int)checkinCount);
			cs.setClassId(ci.getClassId());
			cs.setClassName(ci.getClassName());
			cs.setEndDate(eDate);
			cs.setStartDate(sDate);
			cs.setStudentCount(registerInfoList.size());

			cs.setTeacherName(ci.getTeacherName());
			
			int minRegisterFee = 100000;
			int totalFee = 0;
			for (RegisterInfo ri : registerInfoList) {
				if (ri.getRegisterMode() == 0) {
					// 只有微信报名才计入教师费用统计
					if (minRegisterFee > ri.getRegisterFee()) {
						minRegisterFee = ri.getRegisterFee();
					}
				}
				totalFee += ri.getRegisterFee();
			}
			
			float rateFee = teacherInfo.getClassFeeRate() * registerInfoList.size() *
					minRegisterFee * checkinCount / ci.getClassCount();
			float minFee = teacherInfo.getMinClassFee() * checkinCount;
			cs.setTeacherRateFee(rateFee);
			cs.setTeacherMinFee((int)minFee);
			
			float salary = rateFee < minFee ? rateFee : minFee;
			cs.setTeacherSalary((int)salary);
			cs.setTotalFee(totalFee);
			
			csList.add(cs);
		}
		
		Pagination<ClassStatistic> csPage = new Pagination<ClassStatistic>();
		
		csPage.setElements(csList);
		csPage.setPageNumber(classInfoPage.getPageNumber());
		csPage.setPageSize(classInfoPage.getPageSize());
		csPage.setTotalElements(classInfoPage.getTotalElements());
		
		return csPage;
	}
}
