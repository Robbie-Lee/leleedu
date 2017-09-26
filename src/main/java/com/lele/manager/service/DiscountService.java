package com.lele.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.DiscountDAO;
import com.lele.manager.entity.Discount;
import com.lele.manager.sys.dao.Pagination;

@Service("discountService")
public class DiscountService {

	@Autowired
	DiscountDAO discountDao;

	public Discount getDiscount(int fee) {
		Discount discount = discountDao.getDiscount(fee);
		
		if (discount == null) {
			discount = new Discount();
			discount.setDiscountRate(1.0f);
			discount.setLowerFee(fee);
			discount.setUpperFee(fee);
		}
		
		return discount;
	}
	
	public Pagination<Discount> getDiscountInfo(int curPage, int pageSize) {
		return discountDao.getDiscountInfo(curPage, pageSize);
	}
	
	public int saveDiscount(int lowerFee, int upperFee, float discountRate) {
		
		if (lowerFee > upperFee || lowerFee < 0 || discountRate <= 0.0 || discountRate > 1.0) {
			return -1;
		}
		
		if (discountDao.getDiscount(lowerFee, upperFee) != null) {
			return -2;
		}
		
		if (getDiscount(lowerFee) != null || getDiscount(upperFee) != null) {
			return -3;
		}
		
		Discount dis = new Discount();
		dis.setDiscountRate(discountRate);
		dis.setLowerFee(lowerFee);
		dis.setUpperFee(upperFee);
		
		discountDao.save(dis);
		
		return 0;
	}
}
