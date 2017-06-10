package com.lele.wechat.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lele.manager.utils.HttpRequester;
import com.lele.wechat.dao.PayInfoDAO;
import com.lele.wechat.entity.PayInfo;
import com.lele.wechat.entity.WechatPayBase;
import com.lele.wechat.entity.WechatPayExtendA;
import com.lele.wechat.entity.WechatPayExtendB;
import com.lele.wechat.entity.WechatPayInfo;
import com.thoughtworks.xstream.XStream;

@Service("wechatPayService")
public class WechatPayService {

	private final String appId = "";
	private final String mchId = "";
	private final String body = "乐乐奥数-奥数课程";
	private final String tradeType = "JSAPI";
	private final String payKey = "";
	private final String notifyUrl = "";
	
	@Autowired
	PayInfoDAO payInfoDao;
	
	private static String byteToStr(byte[] byteArray) {  

        String strDigest = "";  

        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  

        return strDigest;  
    }  
	
	private static String byteToHexStr(byte mByte) {  

        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  

        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
        String s = new String(tempArr);  

        return s;  
    }  
	
	private String genRandomStr() {
		return String.valueOf(new Random(new Date().getTime()).nextLong());
	}
	
	private String getSign(String[] params, String[] values) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0;i < params.length;i ++) {
			sb.append(params[i]);
			sb.append("=");
			sb.append(values[i]);
			sb.append("&");
		}
		sb.append("key=" + payKey);

		try {
			return byteToStr(MessageDigest.getInstance("MD5").digest(sb.toString().getBytes())).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public int wechatPayCallback(String callbackInfo) {
		
	}
	
	public WechatPayInfo getPayInfo(String studentId, String classId, String clientIp, int payFee) {
		
		final String outTradeNo = classId + "_" + studentId;
		
		final String wechatPrePayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder?";
		
		final String paramStr = "appid=?0&" 
							  + "body=?1&"
							  + "mch_id=?2&"
							  + "nonce_str=?3&"
							  + "notify_url=?4&"
							  + "openid=?5&"
							  + "out_trade_no=?6&"
							  + "spbill_create_ip=?7&"
							  + "total_fee=?8&"
							  + "trade_type=?9";

		String[] params = {"appid","body","mch_id","nonce_str","notify_url","openid",
								"out_trade_no","spbill_create_ip","total_fee","trade_type"};
		
		String[] values = {appId, body, mchId, genRandomStr(), notifyUrl, classId,
									outTradeNo, clientIp, String.valueOf(payFee), tradeType};
		
		String sign = getSign(params, values);

		String prePayResponse = HttpRequester.HttpGetAccess(wechatPrePayUrl+paramStr+"&sign=?a", values, sign);

		XStream xstream = new XStream();
		WechatPayBase wechatPayBase = (WechatPayExtendA)xstream.fromXML(prePayResponse);
		// JSON.parseObject(prePayResponse, WechatPayBase.class);
			
		PayInfo pi = new PayInfo();
		WechatPayInfo wpi = new WechatPayInfo();
		if (wechatPayBase.getReturn_code().equals("SUCCESS")) {
			WechatPayExtendA wechatExtendA = (WechatPayExtendA)xstream.fromXML(prePayResponse); 
			// JSON.parseObject(prePayResponse, WechatPayExtendA.class);
				
			if (wechatExtendA.getResult_code().equals("SUCCESS")) {
				WechatPayExtendB wechatExtendB = (WechatPayExtendB)xstream.fromXML(prePayResponse);
				// JSON.parseObject(prePayResponse, WechatPayExtendB.class);	
					
				String nonStr = genRandomStr();
				String timeStamp = String.valueOf(new Date().getTime());
					
				String[] ps = {"appId", "nonceStr", "package", "signType", "timeStamp"};
				String[] vs = {appId, nonStr, "prepay_id="+wechatExtendB.getPrepay_id(), "MD5", timeStamp};
					
				pi.setClassId(classId);
				pi.setClientIp(clientIp);
				pi.setPayError("");
				pi.setPayFee(payFee);
				pi.setPayStatus(0);
				pi.setPrepayId(wechatExtendB.getPrepay_id());
				pi.setStudentId(studentId);
				
				wpi.setWechatAppId(appId);
				wpi.setSignType("MD5");
				wpi.setNonceStr(nonStr);
				wpi.setPrepayId("prepay_id="+wechatExtendB.getPrepay_id());
				wpi.setTimeStamp(timeStamp);
				wpi.setPaySign(getSign(ps, vs));
				wpi.setStatus(0);
				wpi.setErrMsg("");
					
				return wpi;
			}
			else {
				System.out.println("Request pre pay code result error: " + wechatExtendA.getErr_code());
				pi.setPayStatus(2);
				pi.setPayError(wechatExtendA.getErr_code());
				
				wpi.setStatus(1);
				wpi.setErrMsg(wechatExtendA.getErr_code_des());
			}
		}
		else {
			System.out.println("Request pre pay code return error: " + wechatPayBase.getReturn_msg());
			pi.setPayStatus(2);
			pi.setPayError(wechatPayBase.getReturn_msg());
			
			wpi.setStatus(1);
			wpi.setErrMsg(wechatPayBase.getReturn_msg());
		}

		payInfoDao.save(pi);
		
		return wpi;
	}
}
