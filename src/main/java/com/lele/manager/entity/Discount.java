package com.lele.manager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discount")
public class Discount implements Serializable {

	private static final long serialVersionUID = -5983871059588465093L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int lowerFee;

	private int upperFee;

	private float discountRate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(float discountRate) {
		this.discountRate = discountRate;
	}

	public int getLowerFee() {
		return lowerFee;
	}

	public void setLowerFee(int lowerFee) {
		this.lowerFee = lowerFee;
	}

	public int getUpperFee() {
		return upperFee;
	}

	public void setUpperFee(int upperFee) {
		this.upperFee = upperFee;
	}
}
