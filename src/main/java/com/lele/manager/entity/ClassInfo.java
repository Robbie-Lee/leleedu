package com.lele.manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "classInfo")
public class ClassInfo implements Serializable {

	private static final long serialVersionUID = -4508521063855573998L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String classId;
	
	private String className;
	
	private String classRoom;
	
	private Date startDate;
	
	private Date endDate;
	
	private String classTime;
	
	private String teacherName;
	
	private int classCount;
	
	private int classPrice;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="classBaseScore")
	private ScoreLevel baseScoreLevel;
	
	private boolean acceptDiscount;
	
	private String classDescription;
	
	private int registerCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public ScoreLevel getBaseScoreLevel() {
		return baseScoreLevel;
	}
	
	public void SetBaseScoreLevel(ScoreLevel scoreLevel) {
		this.baseScoreLevel = scoreLevel;
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

	public int getClassPrice() {
		return classPrice;
	}

	public void setClassPrice(int classPrice) {
		this.classPrice = classPrice;
	}

	public boolean isAcceptDiscount() {
		return acceptDiscount;
	}

	public void setAcceptDiscount(boolean acceptDiscount) {
		this.acceptDiscount = acceptDiscount;
	}

	public String getClassDescription() {
		return classDescription;
	}

	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	public int getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}

	
}
