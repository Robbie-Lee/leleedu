package com.lele.manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "register_info")
public class RegisterInfo implements Serializable {

	private static final long serialVersionUID = -7682620259367194500L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String classId;
	
	private String studentId;
	
	private Date registerDate;
	
	private int registerFee;
	
	private int classScore;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public int getRegisterFee() {
		return registerFee;
	}

	public void setRegisterFee(int registerFee) {
		this.registerFee = registerFee;
	}

	public int getClassScore() {
		return classScore;
	}

	public void setClassScore(int classScore) {
		this.classScore = classScore;
	}
}
