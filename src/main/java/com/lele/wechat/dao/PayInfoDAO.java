package com.lele.wechat.dao;

import org.springframework.stereotype.Repository;

import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.wechat.entity.PayInfo;

@Repository("payInfoDAO")
public class PayInfoDAO extends MysqlBaseDAO<PayInfo> {
	private final String HQL_ENTITY = "PayInfo"; 
}
