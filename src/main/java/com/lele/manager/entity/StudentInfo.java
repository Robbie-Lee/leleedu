package com.lele.manager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "student_info")
public class StudentInfo implements Serializable {

	private static final long serialVersionUID = -1389849585600077593L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String studentId;
	
	private String name;
	
	private String sex;
	
	private int grade;
	
	private String school;
	
	private int guarder;
	
	private String guarderName;
	
	private String guarderPhone;
	
	private String note;
	
	private int totalFee;
	
	@Transient
	private float discountRate;
	
	@ManyToOne
	@JoinColumn(name = "scoreIndex", insertable = true, updatable = true, 
				nullable = false, referencedColumnName="scoreIndex")
	private ScoreLevel scoreLevel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getGuarder() {
		return guarder;
	}

	public void setGuarder(int guarder) {
		this.guarder = guarder;
	}

	public String getGuarderName() {
		return guarderName;
	}

	public void setGuarderName(String guarderName) {
		this.guarderName = guarderName;
	}

	public String getGuarderPhone() {
		return guarderPhone;
	}

	public void setGuarderPhone(String guarderPhone) {
		this.guarderPhone = guarderPhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ScoreLevel getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(ScoreLevel scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public float getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(float discountRate) {
		this.discountRate = discountRate;
	}
}
