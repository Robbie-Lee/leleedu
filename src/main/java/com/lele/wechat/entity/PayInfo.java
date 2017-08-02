package com.lele.wechat.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pay_info")
public class PayInfo implements Serializable {

	private static final long serialVersionUID = 314123410027671696L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String studentId;
	
	private String classId;
	
	private String clientIp;

	private String prepayId;
	
	private int prepayFee;
	
	private String preTradeNo;
	
	private String tradeType;

	private int payFee;

	private String transactionId;
	
	private String tradeNo;
	
	private String payTime;
	
	private int payStatus;
	
	private String payError;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPayError() {
		return payError;
	}

	public void setPayError(String payError) {
		this.payError = payError;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getPayFee() {
		return payFee;
	}

	public void setPayFee(int payFee) {
		this.payFee = payFee;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(int prepayFee) {
		this.prepayFee = prepayFee;
	}

	public String getPreTradeNo() {
		return preTradeNo;
	}

	public void setPreTradeNo(String preTradeNo) {
		this.preTradeNo = preTradeNo;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
}
