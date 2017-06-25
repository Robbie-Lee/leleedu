package com.lele.manager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnrollInfo implements Serializable {

	private static final long serialVersionUID = -2036424598180627647L;

	public class EnrollClass {
	
		private String classId;
	
		private String className;
	
		private String teacherName;
	
		private int classCount;
	
		private int checkinCount;
	
		private int classScore;

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

		public int getCheckinCount() {
			return checkinCount;
		}

		public void setCheckinCount(int checkinCount) {
			this.checkinCount = checkinCount;
		}

		public int getClassScore() {
			return classScore;
		}

		public void setClassScore(int classScore) {
			this.classScore = classScore;
		}
	}
	
	private List<EnrollClass> enrollClass = new ArrayList<EnrollClass>();
	
	private ScoreLevel scoreLevel;
	
	public void addEnrollClass(String classId, String className, String teacherName,
					int classCount, int checkinCount,  int classScore) {
		EnrollClass ec = new EnrollClass();
		ec.setClassId(classId);
		ec.setClassName(className);
		ec.setTeacherName(teacherName);
		ec.setClassCount(classCount);
		ec.setCheckinCount(checkinCount);
		ec.setClassScore(classScore);
		
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
	}
}
