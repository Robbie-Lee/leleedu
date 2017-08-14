package com.lele.manager.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "register_class")
public class RegisterClass implements Serializable {

	private static final long serialVersionUID = 7408495959255111369L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
    @ManyToOne(cascade = {CascadeType.ALL})  
    @JoinColumn(name="registerKeyId")  
	private RegisterInfo registerInfo;

    @ManyToOne(cascade = {CascadeType.ALL})  
    @JoinColumn(name="classKeyId")  
	private ClassInfo classInfo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RegisterInfo getRegisterInfo() {
		return registerInfo;
	}

	public void setRegisterInfo(RegisterInfo registerInfo) {
		this.registerInfo = registerInfo;
	}

	public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
}
