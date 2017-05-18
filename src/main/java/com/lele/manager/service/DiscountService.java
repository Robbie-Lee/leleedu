package com.lele.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.DiscountDAO;
import com.lele.manager.entity.Discount;

@Service("discountService")
public class DiscountService {

	@Autowired
	DiscountDAO discountDao;

	public float getDiscountRate(int fee) {
		return discountDao.getDiscountRate(fee);
	}
	
	public Discount getDiscount(int fee) {
		return discountDao.getDiscount(fee);
	}
}
