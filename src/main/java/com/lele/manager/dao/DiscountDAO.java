package com.lele.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.Discount;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;

@Repository("discountDAO")
public class DiscountDAO extends MysqlBaseDAO<Discount> {

	private final String HQL_ENTITY = "Discount";
	
	public Pagination<Discount> getDiscountInfo(int curPage, int pageSize) {
		final String hql = "from " + HQL_ENTITY;
		return this.doQuery(hql, curPage, pageSize);
	}
	
	public Discount getDiscount(int fee) {
		
		final String hql = "from " + HQL_ENTITY + " where lowerFee <= ?0 and upperFee > ?1";
		Discount discount = this.doQueryUnique(hql, fee, fee);
		return discount;
	}
	
	public Discount getDiscount(int lowerFee, int upperFee) {
		final String hql = "from " + HQL_ENTITY + " where lowerFee = ?0 and upperFee = ?1";
		Discount discount = this.doQueryUnique(hql, lowerFee, upperFee);
		return discount;
	}
}
