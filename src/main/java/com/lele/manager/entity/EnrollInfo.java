package com.lele.manager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class EnrollInfo implements Serializable {

	private static final long serialVersionUID = -2036424598180627647L;

	private List<Map> enrollClass = new ArrayList<Map>();

	private ScoreLevel scoreLevel;



	public ScoreLevel getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(ScoreLevel scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public List<Map> getEnrollClass() {
		return enrollClass;
	}

	public void setEnrollClass(List<Map> list) {
		this.enrollClass = list;
	}
	
	
/*	public class EnrollClass {
	
		private String classId;
	
		private String className;
	
		private String teacherName;
	
		private int classCount;
	
		private String classRoom;
		
		private Date startDate;
		
		private Date endDate;
		
		private String classTime;
		
		private int classPrice;

		public String getClassId() {
			return classId;
		}

		public void setClassId(String classId) {
			this.classId = classId;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getTeacherName() {
			return teacherName;
		}

		public void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}

		public int getClassCount() {
			return classCount;
		}

		public void setClassCount(int classCount) {
			this.classCount = classCount;
		}

		public String getClassRoom() {
			return classRoom;
		}

		public void setClassRoom(String classRoom) {
			this.classRoom = classRoom;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public String getClassTime() {
			return classTime;
		}

		public void setClassTime(String classTime) {
			this.classTime = classTime;
		}

		public int getClassPrice() {
			return classPrice;
		}

		public void setClassPrice(int classPrice) {
			this.classPrice = classPrice;
		}

	}
	
	private List<EnrollClass> enrollClass = new ArrayList<EnrollClass>();
	
	private ScoreLevel scoreLevel;
	
	public void addEnrollClass(String classId, String className, String teacherName,
					int classCount, String classRoom, Date startDate, Date endDate, String classTime, int classPrice) {
		EnrollClass ec = new EnrollClass();
		ec.setClassId(classId);
		ec.setClassName(className);
		ec.setTeacherName(teacherName);
		ec.setClassCount(classCount);
		ec.setClassRoom(classRoom);
		ec.setStartDate(startDate);
		ec.setEndDate(endDate);
		ec.setClassTime(classTime);
		ec.setClassPrice(classPrice);
		
		enrollClass.add(ec);
	}

	public ScoreLevel getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(ScoreLevel scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public List<EnrollClass> getEnrollClass() {
		return enrollClass;
	}

	public void setEnrollClass(List<EnrollClass> enrollClass) {
		this.enrollClass = enrollClass;
	}*/
}
