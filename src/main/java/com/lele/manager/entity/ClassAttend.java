package com.lele.manager.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "class_attend")
public class ClassAttend implements Serializable {

	private static final long serialVersionUID = 4388322065382773897L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "classId",referencedColumnName="classId")
	private ClassInfo classInfo;
	
	private String teacherId;
	
	private int attendCount;
	
//	@Transient
//	private String teacherName;
	
	public ClassInfo getClassInfo() {
		return this.classInfo;
	}
	
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getAttendCount() {
		return attendCount;
	}

	public void setAttendCount(int attendCount) {
		this.attendCount = attendCount;
	}

//	public String getTeacherName() {
//		return teacherName;
//	}

//	public void setTeacherName(String teacherName) {
//		this.teacherName = teacherName;
//	}
}
