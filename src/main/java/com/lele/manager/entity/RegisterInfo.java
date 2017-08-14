package com.lele.manager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "register_info")
public class RegisterInfo implements Serializable {

	private static final long serialVersionUID = -7682620259367194500L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "register_class", joinColumns = { @JoinColumn(name = "registerKeyId", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "classKeyId", nullable = false, updatable = false) })
	private Set<ClassInfo> classInfos;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "register_student", joinColumns = { @JoinColumn(name = "registerKeyId", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "studentKeyId", nullable = false, updatable = false) })
	private Set<StudentInfo> studentInfos;
	
	private Date registerDate;
	
	private int registerMode;  
	
	private int payFee;
	
	private int payMode;	
	
	private int classScore;
	
	private int registerChannel;  
	
	private String note;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public int getClassScore() {
		return classScore;
	}

	public void setClassScore(int classScore) {
		this.classScore = classScore;
	}

	public int getRegisterMode() {
		return registerMode;
	}

	public void setRegisterMode(int registerMode) {
		this.registerMode = registerMode;
	}

	public int getPayFee() {
		return payFee;
	}

	public void setPayFee(int payFee) {
		this.payFee = payFee;
	}

	public int getPayMode() {
		return payMode;
	}

	public void setPayMode(int payMode) {
		this.payMode = payMode;
	}

	public int getRegisterChannel() {
		return registerChannel;
	}

	public void setRegisterChannel(int registerChannel) {
		this.registerChannel = registerChannel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<ClassInfo> getClassInfos() {
		return classInfos;
	}

	public void setClassInfos(Set<ClassInfo> classInfos) {
		this.classInfos = classInfos;
	}

	public void setClassInfo(ClassInfo classInfo) {
		
		if (this.classInfos == null) {
			this.classInfos = new HashSet<ClassInfo>();
		}
		
		this.classInfos.add(classInfo);
	}
	
	public Set<StudentInfo> getStudentInfos() {
		return studentInfos;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		if (this.studentInfos == null) {
			this.studentInfos = new HashSet<StudentInfo>();
		}
		
		this.studentInfos.add(studentInfo);
	}
	
	public void setStudentInfos(Set<StudentInfo> studentInfos) {
		this.studentInfos = studentInfos;
	}

}
