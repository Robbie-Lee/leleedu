package com.lele.wechat.dao;

import org.springframework.stereotype.Repository;

import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.wechat.entity.PayInfo;

@Repository("payInfoDAO")
public class PayInfoDAO extends MysqlBaseDAO<PayInfo> {
	private final String HQL_ENTITY = "PayInfo"; 

	public PayInfo getPayInfoByTradeNo(String openId, String outTradeNo) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0 and tradeNo = ?1";
		return this.doQueryUnique(hql, openId, outTradeNo);
	}

	public PayInfo getPayInfoByPreTradeNo(String openId, String outTradeNo) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0 and preTradeNo = ?1";
		return this.doQueryUnique(hql, openId, outTradeNo);
	}
	
	public PayInfo getPayInfo(String prepayId) {
		final String hql = "from " + HQL_ENTITY + " where prepayId = ?0";
		return this.doQueryUnique(hql, prepayId);
	}

	public PayInfo getPayInfoBySCId(String studentId, String classId) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0 and classId = ?1 and payStatus = ?2";
		return this.doQueryUnique(hql, studentId, classId, 1);
	}
}
