package com.lele.manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "class_info")
public class ClassInfo implements Serializable {

	private static final long serialVersionUID = -4508521063855573998L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String classId;
	
	private String className;
	
	private String classRoom;
	
	@Temporal(value = TemporalType.DATE)
	private Date startDate;
	
	@Temporal(value = TemporalType.DATE)
	private Date endDate;
	
	private String classTime;
	
	private String teacherName;
	
	private int classCount;
	
	private int classPrice;
	
//	@OneToOne(mappedBy = "classInfo", fetch = FetchType.LAZY)
//	private ClassAttend classAttend;
	
	@ManyToOne
	@JoinColumn(name = "classBaseScore", insertable = true, updatable = true, 
				nullable = false, referencedColumnName="scoreIndex")
	private ScoreLevel scoreLevel;
	
	private boolean acceptDiscount;
	
	private String classDescription;
	
	private int registerCount;
	
	private int checkinCount;
	
	private int classGrade;
	
	private int registerLimit;
	
	private boolean valid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public ScoreLevel getScoreLevel() {
		return scoreLevel;
	}
	
	public void setScoreLevel(ScoreLevel scoreLevel) {
		this.scoreLevel = scoreLevel;
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

	@Temporal(value = TemporalType.DATE)
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

	public int getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(int checkinCount) {
		this.checkinCount = checkinCount;
	}

	public int getClassGrade() {
		return classGrade;
	}

	public void setClassGrade(int classGrade) {
		this.classGrade = classGrade;
	}

	public int getRegisterLimit() {
		return registerLimit;
	}

	public void setRegisterLimit(int registerLimit) {
		this.registerLimit = registerLimit;
	}

	public boolean isAcceptDiscount() {
		return acceptDiscount;
	}

	public void setAcceptDiscount(boolean acceptDiscount) {
		this.acceptDiscount = acceptDiscount;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

/*	
	@OneToOne(mappedBy = "classInfo", fetch = FetchType.LAZY)
	public ClassAttend getClassAttend() {
		return this.classAttend;
	}
	
	public void setClassAttend(ClassAttend classAttend) {
		this.classAttend = classAttend;
	}
*/	
}
