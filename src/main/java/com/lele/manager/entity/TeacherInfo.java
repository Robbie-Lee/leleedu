package com.lele.manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lele.manager.utils.TimeUtils;

@Entity
@Table(name = "teacher_info")
public class TeacherInfo implements Serializable {

	private static final long serialVersionUID = 6483537812476043785L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String name;
	
	private String teacherId;
	
	private String sex;
	
	private String degree;
	
	private String major;
	
	private String college;
	
	private Date birthDay;
	
	@Transient
	private int age;
	
	private int teachAge;
	
	private float classFeeRate;
	
	private int minClassFee;
	
	private Date checkInTime;
	
	private String phone;
	
	private int status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public int getTeachAge() {
		return teachAge;
	}

	public void setTeachAge(int teachAge) {
		this.teachAge = teachAge;
	}

	public float getClassFeeRate() {
		return classFeeRate;
	}

	public void setClassFeeRate(float classFeeRate) {
		this.classFeeRate = classFeeRate;
	}

	public int getMinClassFee() {
		return minClassFee;
	}

	public void setMinClassFee(int minClassFee) {
		this.minClassFee = minClassFee;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAge() {
		return (int) TimeUtils.getBetween(birthDay, new Date(), TimeUtils.YEAR_RETURN);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
}
