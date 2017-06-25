package com.lele.wechat.dao;

import org.springframework.stereotype.Repository;

import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.wechat.entity.PayInfo;

@Repository("payInfoDAO")
public class PayInfoDAO extends MysqlBaseDAO<PayInfo> {
	private final String HQL_ENTITY = "PayInfo"; 
	
	public PayInfo getPayInfo(String openId, int totalFee) {
		final String hql = "from " + HQL_ENTITY + " where sutdentId = ?0 and prepayFee = ?1";
		return this.doQueryUnique(hql, openId, totalFee);
	}
	
	public PayInfo getPayInfo(String prepayId) {
		final String hql = "from " + HQL_ENTITY + " where prepayId = ?0";
		return this.doQueryUnique(hql, prepayId);
	}
}
