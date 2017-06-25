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
	private final String body = "乐乐奥数-奥数课程";
	private final String tradeType = "JSAPI";
	
	private final String notifyUrl = "http://wx.leleedu.com/lele/wechatPay/wechatpayback.json";
	
	@Autowired
	PayInfoDAO payInfoDao;
		
	private String genRandomStr() {
		return String.valueOf(new Random(new Date().getTime()).nextLong());
	}
	
	public int clientPayCallback(String prepayId) {
		
		PayInfo payInfo = payInfoDao.getPayInfo(prepayId);
		if (payInfo.getPayStatus() != 0) {
			return payInfo.getPayStatus();
		}
		
		// 查询  https://api.mch.weixin.qq.com/pay/orderquery
		String outTradeNo = payInfo.getClassId() + "_" + payInfo.getStudentId();
		outTradeNo = outTradeNo.substring(0, 32);
		outTradeNo = outTradeNo.replace("?", "@");
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			payInfo.setTradeType(wechatExtendB.getTrade_type());
			payInfo.setPayFee(wechatExtendB.getTotal_fee());
			payInfo.setTradeNo(wechatExtendB.getOut_trade_no());
			payInfo.setTransactionId(wechatExtendB.getTransaction_id());
			payInfo.setPayTime(wechatExtendB.getTime_end());
			
			if (wechatExtendB.getErr_code() != null) {
				payInfo.setPayStatus(2);
				payInfo.setPayError(wechatExtendB.getErr_code());
				payInfoDao.save(payInfo);
				return 2;

			}
			else {
				payInfo.setPayStatus(1);
				payInfoDao.save(payInfo);
				return 1;
			}
		}
		else {
			return 2;
		}
	}
	
	public String wechatPayCallback(String callbackInfo) {
		
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", WechatPayExtendA.class);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        WechatPayExtendA wechatExtendA = (WechatPayExtendA) xStreamForResponseData.fromXML(callbackInfo);

		if (wechatExtendA.getReturn_code().equals("SUCCESS")) {
			
			try {
				if (!Signature.checkIsSignValidFromResponseString(callbackInfo)) {
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
				return "FAIL";
			}

			if (!wechatExtendA.getMch_id().equals(mchId)) {
				return "FAIL";
			}
			
			PayInfo payInfo = payInfoDao.getPayInfo(wechatExtendA.getOpenid(), wechatExtendA.getTotal_fee());
			if (payInfo == null) {
				return "FAIL";
			}

			if (wechatExtendA.getTotal_fee() != payInfo.getPrepayFee()) {
				return "FAIL";
			}

			payInfo.setTradeType(wechatExtendA.getTrade_type());
			payInfo.setPayFee(wechatExtendA.getTotal_fee());
			payInfo.setTradeNo(wechatExtendA.getOut_trade_no());
			payInfo.setTransactionId(wechatExtendA.getTransaction_id());
			payInfo.setPayTime(wechatExtendA.getTime_end());
			
			if (wechatExtendA.getErr_code() != null) {
				payInfo.setPayStatus(2);
				payInfo.setPayError(wechatExtendA.getErr_code());
				payInfoDao.save(payInfo);
				return "SUCCESS";

			}
			else {
				payInfo.setPayStatus(1);
				payInfoDao.save(payInfo);
				return "FAIL";
			}
		}
		else {
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
		
		String outTradeNo = classId + "-" + studentId;
		outTradeNo = outTradeNo.substring(0, 32);
		outTradeNo = outTradeNo.replace("?", "@");
		
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
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        WechatPrePay wechatPrePay = (WechatPrePay) xStreamForResponseData.fromXML(prePayResponse);

		PayInfo pi = new PayInfo();
		WechatPrePayInfo wpi = new WechatPrePayInfo();
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
				String timeStamp = String.valueOf(new Date().getTime());
					
				pi.setClassId(classId);
				pi.setClientIp(clientIp);
				pi.setPayError("");
				pi.setPayFee(payFee);
				pi.setPayStatus(0);
				pi.setPrepayId(wechatPrePay.getPrepay_id());
				pi.setStudentId(studentId);
				
				Map<String, Object> pm = new HashMap<String, Object>();
				pm.put("appid", appId);
				pm.put("nonce_str", nonStr);
				pm.put("package", "prepay_id="+wechatPrePay.getPrepay_id());
				pm.put("signType", "MD5");
				pm.put("timeStamp", timeStamp);

				String toPageSign = Signature.getSign(pm);

				wpi.setWechatAppId(appId);
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
				System.out.println("Request pre pay code result error: " + wechatPrePay.getErr_code());
				pi.setPayStatus(2);
				pi.setPayError(wechatPrePay.getErr_code());
				
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
