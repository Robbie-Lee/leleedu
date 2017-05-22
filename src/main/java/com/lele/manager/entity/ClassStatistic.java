package com.lele.manager.entity;

import java.io.Serializable;
import java.util.Date;

public class ClassStatistic implements Serializable {

	private static final long serialVersionUID = 746305795222067568L;

	private String teacherName;
	
	private Date startDate;
	
	private Date endDate;
	
	private String classId;
	
	private String className;
	
	private int attendCount;
	
	private int studentCount;
	
	private int totalFee;
	
	// 比例课时费
	private float teacherRateFee;
	// 保底课时费
	private int teacherMinFee;
	
	private int teacherSalary;

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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

	public int getAttendCount() {
		return attendCount;
	}

	public void setAttendCount(int attendCount) {
		this.attendCount = attendCount;
	}

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public float getTeacherRateFee() {
		return teacherRateFee;
	}

	public void setTeacherRateFee(float teacherRateFee) {
		this.teacherRateFee = teacherRateFee;
	}

	public int getTeacherMinFee() {
		return teacherMinFee;
	}

	public void setTeacherMinFee(int teacherMinFee) {
		this.teacherMinFee = teacherMinFee;
	}

	public int getTeacherSalary() {
		return teacherSalary;
	}

	public void setTeacherSalary(int teacherSalary) {
		this.teacherSalary = teacherSalary;
	}
}
