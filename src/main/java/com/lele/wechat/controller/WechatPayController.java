package com.lele.wechat.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lele.wechat.service.WechatPayService;
import com.lele.wechat.vo.WechatPrePay;
import com.lele.wechat.vo.WechatReturn;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Controller
@RequestMapping("/wechatPay")
public class WechatPayController {

	@Autowired
	WechatPayService wechatPayService;
	
	@RequestMapping(value="/prepay.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object prepay(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "clientIp", required = true) String clientIp,
			@RequestParam(value = "payFee", required = true) int payFee) throws Exception {

		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];
		
		System.out.println("prepay.json: studentId " + studentId + "classId " + classId
						+ "clientIp " + clientIp + "payFee " + payFee);
		return wechatPayService.getPayInfo(studentId, classId, clientIp, payFee);
	}
	
	@RequestMapping(value="/wechatpayback.json", method = RequestMethod.POST)
	public @ResponseBody 
	Object wechatpayback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("get pay callback from wechat");
		
//		PrintWriter pw = response.getWriter();
		
		StringBuffer xmlStr = new StringBuffer(); 
		try {
			BufferedReader reader = request.getReader(); 
			String line = null; 
			while ((line = reader.readLine()) != null) { 
				xmlStr.append(line); 
			}
		}
		catch (Exception e) { 
			
		} 
		
		System.out.println("get pay callback from wechat, body is " + xmlStr.toString());
		
		String processResult = wechatPayService.wechatPayCallback(xmlStr.toString());

		WechatReturn wr = new WechatReturn();
		if (processResult.equals("SUCCESS")) {
			wr.setReturn_code("SUCCESS");
			wr.setReturn_msg("OK");
		}
		else {
			wr.setReturn_code("FAIL");
			wr.setReturn_msg("Inner Error");
		}

        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.alias("xml", WechatReturn.class);

		System.out.println("get pay callback from wechat, response is " + xStream.toXML(wr));

//        response.getWriter().write(xStream.toXML(wr).toString());
		return xStream.toXML(wr);
	}

	@RequestMapping(value="/clientpayback.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object clientpayback(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "prepayId", required = true) String prepayId) throws Exception {
		
		return wechatPayService.clientPayCallback(prepayId);
	}
}
