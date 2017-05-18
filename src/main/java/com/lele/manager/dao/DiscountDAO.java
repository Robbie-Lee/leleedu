package com.lele.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.Discount;
import com.lele.manager.sys.dao.MysqlBaseDAO;

@Repository("discountDAO")
public class DiscountDAO extends MysqlBaseDAO<Discount> {

	private final String HQL_ENTITY = "Discount";
	
	public float getDiscountRate(int fee) {
		
		final String hql = "from " + HQL_ENTITY + " where totalFee <= ?0";
		
		List<Discount> disList = this.doQueryList(hql, fee);
		
		if (disList == null) {
			return 1.0f;
		}
		
		float minDiscountRate = 1.0f;
		for (Discount dis : disList) {
			if (dis.getDiscountRate() < minDiscountRate) {
				minDiscountRate = dis.getDiscountRate();
			}
		}
		
		return minDiscountRate;
	}
	
	public Discount getDiscount(int fee) {
		
		final String hql = "from " + HQL_ENTITY + " where totalFee <= ?0";
		
		List<Discount> disList = this.doQueryList(hql, fee);
		
		if (disList == null) {
			Discount ds = new Discount();
			ds.setDiscountRate(1.0f);
			ds.setTotalFee(fee);
			
			return ds;
		}
		
		float minDiscountRate = 1.0f;
		for (Discount dis : disList) {
			if (dis.getDiscountRate() < minDiscountRate) {
				minDiscountRate = dis.getDiscountRate();
			}
		}
		
		Discount ds = new Discount();
		ds.setDiscountRate(minDiscountRate);
		ds.setTotalFee(fee);
		
		return ds;
	}
}
