package com.lele.wechat.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.lele.manager.dao.ClassInfoDAO;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.dao.StudentInfoDAO;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.utils.HttpRequester;
import com.lele.wechat.dao.PayInfoDAO;
import com.lele.wechat.entity.PayInfo;
import com.lele.wechat.utils.Signature;
import com.lele.wechat.vo.PrepayVo;
import com.lele.wechat.vo.QueryVo;
import com.lele.wechat.vo.WechatPayExtendA;
import com.lele.wechat.vo.WechatPayExtendB;
import com.lele.wechat.vo.WechatPrePay;
import com.lele.wechat.vo.WechatPrePayInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Service("wechatPayService")
public class WechatPayService {

	private final String appId = "wx352adddd9f085a9a";
	private final String mchId = "1481039182";
	private final String body = "leleedu-class";
	private final String tradeType = "JSAPI";
	
	private final String notifyUrl = "http://wx.leleedu.com/wechatPay/wechatpayback.json";
	
	@Autowired
	PayInfoDAO payInfoDao;
	
	@Autowired
	RegisterInfoDAO registerInfoDao;
	
	@Autowired
	ClassInfoDAO classInfoDao;
	
	@Autowired
	StudentInfoDAO studentInfoDao;
	
	private void enrollComplete(PayInfo payInfo) {
		String classId = payInfo.getClassId();
		String studentId = payInfo.getStudentId();
		int fee = payInfo.getPayFee();
		
		RegisterInfo ri = new RegisterInfo();
		ri.setClassInfo(classInfoDao.getClassInfoById(classId));
		ri.setRegisterDate(new Date());
		ri.setRegisterMode(0); 		// 报名
		ri.setPayFee(fee);
		ri.setPayMode(0);			// 微信支付
		ri.setStudentInfo(studentInfoDao.getStudentInfoById(studentId));
		ri.setClassScore(0);
		ri.setRegisterChannel(0);		// 微信报名
		
		registerInfoDao.save(ri);
		
		System.out.println("enroll complete and update class info for classId: " + classId);
		classInfoDao.enroll(classId, fee);

		System.out.println("enroll complete and update student info for studentId: " + studentId);
		studentInfoDao.plusTotalFee(studentId, fee);
	}
	
	private String genRandomStr() {
		return String.valueOf(new Random(new Date().getTime()).nextLong());
	}
	
	public int clientPayCallback(String prepayId) {
		
		prepayId = prepayId.split("=")[1];
		
		System.out.println("receive client notify and prepayId is " + prepayId);
		PayInfo payInfo = payInfoDao.getPayInfo(prepayId);
		
		if (payInfo == null) {
			System.out.println("can't find prepayInfo in system");
			return 2;
		}
		
		if (payInfo.getPayStatus() != 0) {
			return payInfo.getPayStatus();
		}
		
		// 查询  https://api.mch.weixin.qq.com/pay/orderquery
		String outTradeNo = payInfo.getPreTradeNo();
		
		final String nonceStr = genRandomStr();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", appId);
		paramMap.put("mch_id", mchId);
		paramMap.put("nonce_str", nonceStr);
		paramMap.put("out_trade_no", outTradeNo);
		String sign = Signature.getSign(paramMap);
		
		QueryVo qv = new QueryVo();
		qv.setAppid(appId);
		qv.setMch_id(mchId);
		qv.setNonce_str(nonceStr);
		qv.setOut_trade_no(outTradeNo);
		qv.setSign(sign);
		
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", QueryVo.class);
        String postDataXML = xStreamForRequestPostData.toXML(qv);

		final String queryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
		String queryPayResponse = HttpRequester.HttpPostAccess(queryUrl, postDataXML); 
		
		System.out.println("query pay status from wechat and response is: " + queryPayResponse);

        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", WechatPayExtendB.class);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        WechatPayExtendB wechatExtendB = (WechatPayExtendB) xStreamForResponseData.fromXML(queryPayResponse);
		
		if (wechatExtendB.getReturn_code().equals("SUCCESS")) {
			try {
				if (!Signature.checkIsSignValidFromResponseString(queryPayResponse)) {
					return 2;
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

			if (wechatExtendB.getErr_code() != null) {
				payInfo.setPayStatus(2);
				payInfo.setPayError(wechatExtendB.getErr_code_des());
				payInfoDao.save(payInfo);
				System.out.println("complete pay process for student: " + payInfo.getStudentId() + " and class: " + 
						payInfo.getClassId() + " and error is " + wechatExtendB.getErr_code_des());
				return 2;
			}
			else if (wechatExtendB.getResult_code().equals("SUCCESS")) {
				
				if (wechatExtendB.getTrade_state().equals("SUCCESS")) {
					payInfo.setTradeType(wechatExtendB.getTrade_type());
					payInfo.setPayFee(wechatExtendB.getTotal_fee());
					payInfo.setTradeNo(wechatExtendB.getOut_trade_no());
					payInfo.setTransactionId(wechatExtendB.getTransaction_id());
					payInfo.setPayTime(wechatExtendB.getTime_end());
					
					payInfo.setPayStatus(1);
					payInfoDao.save(payInfo);
					enrollComplete(payInfo);   // 完成报名
					System.out.println("complete pay process for student: " + payInfo.getStudentId() + " and class: " + payInfo.getClassId());
					return 1;
				}
				else {
					payInfo.setTradeType(wechatExtendB.getTrade_type());
					payInfo.setPayFee(wechatExtendB.getTotal_fee());
					payInfo.setTradeNo(wechatExtendB.getOut_trade_no());
					payInfo.setTransactionId(wechatExtendB.getTransaction_id());
					payInfo.setPayTime(wechatExtendB.getTime_end());
					payInfo.setPayError(wechatExtendB.getTrade_state_desc());
					
					payInfo.setPayStatus(2);
					payInfoDao.save(payInfo);
					System.out.println("complete pay process for student: " + payInfo.getStudentId() + " and class: " + 
							payInfo.getClassId() + " and error is " + wechatExtendB.getTrade_state_desc());
					return 2;
				}
			}
		}

		return 2;
	}
	
	public String wechatPayCallback(String callbackInfo) {
		
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", WechatPayExtendA.class);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        WechatPayExtendA wechatExtendA = (WechatPayExtendA) xStreamForResponseData.fromXML(callbackInfo);

		if (wechatExtendA.getReturn_code().equals("SUCCESS")) {
			PayInfo payInfo = null;
			payInfo = payInfoDao.getPayInfoByTradeNo(wechatExtendA.getOpenid(), wechatExtendA.getOut_trade_no());
			
			if (payInfo != null) {
				return "SUCCESS";
			}

			try {
				if (!Signature.checkIsSignValidFromResponseString(callbackInfo)) {
					System.out.println("pay callback sign is error");
					return "FAIL";
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (!wechatExtendA.getAppid().equals(appId)) {
				System.out.println("pay callback error and cause is appId is incorrect. Return appId is: " + wechatExtendA.getAppid());
				return "FAIL";
			}

			if (!wechatExtendA.getMch_id().equals(mchId)) {
				System.out.println("pay callback error and cause is mchId is incorrect. Return mchId is: " + wechatExtendA.getMch_id());
				return "FAIL";
			}

			try {
				payInfo = payInfoDao.getPayInfoByPreTradeNo(wechatExtendA.getOpenid(), wechatExtendA.getOut_trade_no());
				if (payInfo == null) {
					System.out.println("pay callback error and cause is can't find payInfo. Return openid is: " + wechatExtendA.getOpenid() + " and return outTradeNo is: " + wechatExtendA.getOut_trade_no());
					return "FAIL";
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return "FAIL";
			}
			
			if (wechatExtendA.getTotal_fee() != payInfo.getPrepayFee()) {
				System.out.println("pay callback error and cause is totalFee is incorrect. Return totalFee is: " + wechatExtendA.getTotal_fee());
				return "FAIL";
			}

			payInfo.setTradeType(wechatExtendA.getTrade_type());
			payInfo.setPayFee(wechatExtendA.getTotal_fee());
			payInfo.setTradeNo(wechatExtendA.getOut_trade_no());
			payInfo.setTransactionId(wechatExtendA.getTransaction_id());
			payInfo.setPayTime(wechatExtendA.getTime_end());
			
			if (wechatExtendA.getErr_code() != null) {
				System.out.println("pay callback error and cause is " + wechatExtendA.getErr_code_des());

				payInfo.setPayStatus(2);
				payInfo.setPayError(wechatExtendA.getErr_code_des());
				payInfoDao.save(payInfo);
				return "FAIL";

			}
			else {
				payInfo.setPayStatus(1);
				payInfoDao.save(payInfo);
				enrollComplete(payInfo);
				return "SUCCESS";
			}
		}
		else {
			System.out.println("pay callback error and cause is " + wechatExtendA.getReturn_msg());
			return wechatExtendA.getReturn_msg();
		}
	}

	/*
	@Test
	public void test1() {
		clientPayCallback("wx20170624215140ff90805e100815261554");
	}
	*/
	
	public WechatPrePayInfo getPayInfo(String studentId, String classId, String clientIp, int payFee) {
		
		String outTradeNo = classId + "-" + new Date().getTime();
		if (outTradeNo.length() > 32) {
			outTradeNo = outTradeNo.substring(0, 32);
		}
		// outTradeNo = outTradeNo.replace("?", "@");
		
		final String wechatPrePayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		String nonceStr = genRandomStr();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", appId);
		paramMap.put("body", body);
		paramMap.put("mch_id", mchId);
		paramMap.put("nonce_str", nonceStr);
		paramMap.put("notify_url", notifyUrl);
		paramMap.put("openid", studentId);
		paramMap.put("out_trade_no", outTradeNo);
		paramMap.put("spbill_create_ip", clientIp);
		paramMap.put("total_fee", payFee);
		paramMap.put("trade_type", tradeType);

		String sign = Signature.getSign(paramMap);

		PrepayVo pv = new PrepayVo();
		pv.setAppid(appId);
		pv.setBody(body);
		pv.setMch_id(mchId);
		pv.setNonce_str(nonceStr);
		pv.setNotify_url(notifyUrl);
		pv.setOpenid(studentId);
		pv.setOut_trade_no(outTradeNo);
		pv.setSpbill_create_ip(clientIp);
		pv.setTotal_fee(payFee);
		pv.setTrade_type(tradeType);
		pv.setSign(sign);

        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", PrepayVo.class);
        String postDataXML = xStreamForRequestPostData.toXML(pv);

		String prePayResponse = HttpRequester.HttpPostAccess(wechatPrePayUrl, postDataXML); 

        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", WechatPrePay.class);
        xStreamForResponseData.ignoreUnknownElements();
        WechatPrePay wechatPrePay = (WechatPrePay) xStreamForResponseData.fromXML(prePayResponse);

		PayInfo pi = new PayInfo();
		WechatPrePayInfo wpi = new WechatPrePayInfo();

		pi.setClassId(classId);
		pi.setClientIp(clientIp);
		pi.setStudentId(studentId);
		pi.setPreTradeNo(outTradeNo);

		if (wechatPrePay.getReturn_code().equals("SUCCESS")) {
			
			try {
				if (!Signature.checkIsSignValidFromResponseString(prePayResponse)) {
					wpi.setStatus(1);
					wpi.setErrMsg("sign error");
					return wpi;
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (wechatPrePay.getResult_code().equals("SUCCESS")) {
				String nonStr = genRandomStr();
				String timeStamp = String.valueOf(new Date().getTime()/1000);
					
				pi.setPayError("");
//				pi.setPayFee(payFee);
				pi.setPrepayFee(payFee);
				pi.setPayStatus(0);
				pi.setPrepayId(wechatPrePay.getPrepay_id());
				
				Map<String, Object> pm = new HashMap<String, Object>();
				pm.put("appId", appId);
				pm.put("nonceStr", nonStr);
				pm.put("package", "prepay_id="+wechatPrePay.getPrepay_id());
				pm.put("signType", "MD5");
				pm.put("timeStamp", timeStamp);

				String toPageSign = Signature.getSign(pm);

				wpi.setAppId(appId);
				wpi.setSignType("MD5");
				wpi.setNonceStr(nonStr);
				wpi.setPrepayId("prepay_id="+wechatPrePay.getPrepay_id());
				wpi.setTimeStamp(timeStamp);
				wpi.setPaySign(toPageSign);
				wpi.setStatus(0);
				wpi.setErrMsg("");

				payInfoDao.save(pi);
				return wpi;
			}
			else {
				System.out.println("Request pre pay code result error: " + wechatPrePay.getErr_code_des());
				pi.setPayStatus(2);
				pi.setPayError(wechatPrePay.getErr_code_des());
				
				wpi.setStatus(1);
				wpi.setErrMsg(wechatPrePay.getErr_code_des());
			}
		}
		else {
			System.out.println("Request pre pay code return error: " + wechatPrePay.getReturn_msg());
			pi.setPayStatus(2);
			pi.setPayError(wechatPrePay.getReturn_msg());
			
			wpi.setStatus(1);
			wpi.setErrMsg(wechatPrePay.getReturn_msg());
		}

		payInfoDao.save(pi);
		return wpi;
	}
}
