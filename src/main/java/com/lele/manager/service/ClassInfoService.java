package com.lele.manager.service;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.ScoreLevel;
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

/*	
	public void saveClassInfo() {
		
		ClassInfo classInfo = new ClassInfo();
		classInfo.setAcceptDiscount(acceptDiscount);
		
		
		
		String classId, String className;
		
		private String classRoom;
		
		private Date startDate;
		
		private Date endDate;
		
		private String classTime;
		
		private String teacherName;
		
		private int classCount;
		
		private int classPrice;
		
		@ManyToOne
		@JoinColumn(name = "classBaseScore", insertable = true, updatable = true, 
					nullable = false, referencedColumnName="scoreIndex")
		private ScoreLevel scoreLevel;
		
		private boolean acceptDiscount;
		
		private String classDescription;
		
		private int registerCount;

	}
*/	
}
