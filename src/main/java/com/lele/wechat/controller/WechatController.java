package com.lele.wechat.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.entity.EnrollInfo;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.service.ClassInfoService;
import com.lele.manager.service.RegisterInfoService;
import com.lele.manager.service.ScoreLevelService;
import com.lele.manager.service.StudentInfoService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;
import com.lele.manager.utils.HttpRequester;
import com.lele.wechat.service.WechatService;

@Controller
@RequestMapping("/wechat")
public class WechatController {

	@Autowired
	StudentInfoService studentInfoService;

	@Autowired
	WechatService wechatService;
	
	@Autowired
	ClassInfoService classInfoService;
	
	@Autowired
	ScoreLevelService scoreLevelService;
	
	@Autowired
	RegisterInfoService registerInfoService;

	private final static String token = "lele_edu_wechat";  
	
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
	
	@RequestMapping(value="/search/class.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object searchClass(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classGrade", required = false, defaultValue = "0") int classGrade,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
        
        Pagination<ClassInfo> classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
        														"", "", "", null, null, 0, classGrade);
        
        return classInfoList;  
    }
	
	@RequestMapping(value="/signature.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object signature(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr) throws Exception {
			
		String[] arr = new String[] { token, timestamp, nonce };  

        Arrays.sort(arr);  
        StringBuilder content = new StringBuilder();  

        for (int i = 0; i < arr.length; i++) {  
            content.append(arr[i]);  
        }  

        MessageDigest md = null;  
        String tmpStr = null;  

        try {  
            md = MessageDigest.getInstance("SHA-1");  
            byte[] digest = md.digest(content.toString().getBytes());  
            tmpStr = byteToStr(digest);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  

        if (tmpStr != null) {
        	if (tmpStr.equals(signature.toUpperCase())) {
        		return echostr;
        	}
        }
        
        return null;
	}
	
	// 微信回调到报名接口
	@RequestMapping(value="/callback.json", method = RequestMethod.GET)
	public @ResponseBody 
	void callback(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "state", required = true) String state) throws Exception {
		
		System.out.println("Get wechat callback, code is " + code + ", state is " + state);
		
		/*  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code 
			{ 
				"access_token":"ACCESS_TOKEN",    
	 			"expires_in":7200,    
	 			"refresh_token":"REFRESH_TOKEN",    
	 			"openid":"OPENID",
	 			"scope":"SCOPE" 
	 		} 
		*/
		
		StudentInfo si = studentInfoService.getStudentInfoByCode(code);
		
		if (si != null) {
			//	已经注册过
			if (state.equals("enroll")) {
				response.sendRedirect(request.getContextPath() + "/wechat/enroll.do?wechatid=" + si.getStudentId());
			}
			else if (state.equals("info")) {
				response.sendRedirect(request.getContextPath() + "/wechat/enrollinfo.do?studentId=" + si.getStudentId());
			}
		}
		else {
			final String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=?0&secret=?1&code=?2&grant_type=?3";
			final String appId = "wx352adddd9f085a9a";
			final String appSecret = "ec8642d4540aba1a0fd3b659f3ab0d09";
			final String grantType = "authorization_code";
			
			String weChatResponse = HttpRequester.HttpGetAccess(accessTokenUrl, appId, appSecret, code, grantType);
			System.out.println("WeChat response is : " + weChatResponse);
			
			JSONObject jsonObject = JSON.parseObject(weChatResponse);
			
			String openId = jsonObject.get("openid").toString();
			
			if (studentInfoService.isStudentExist(openId)) {
				if (state.equals("enroll")) {
					response.sendRedirect(request.getContextPath() + "/wechat/enroll.do?wechatid=" + openId);
				}
				else if (state.equals("info")) {
					response.sendRedirect(request.getContextPath() + "/wechat/enrollinfo.do?studentId=" + openId);
				}
			}
			else {
				response.sendRedirect(request.getContextPath() + "/wechat/register.do?wechatid=" + openId + "&wechatcode=" + code);
			}
		}
	}
	
	@RequestMapping(value= {"MP_verify_F4LX5mHlnTvFy2s6.txt"}, method = RequestMethod.GET)
	public @ResponseBody
	void downFile(HttpServletRequest request, HttpServletResponse response) {
				
		File file = new File("/home/leleedu/apache-tomcat-8.5.6/MP_verify_F4LX5mHlnTvFy2s6.txt");
		if (file.exists()) {
			System.out.println("wechat file exist");
			try {
                InputStream ins = new FileInputStream(file);    
                BufferedInputStream bins = new BufferedInputStream(ins);
                OutputStream outs = response.getOutputStream();
                BufferedOutputStream bouts = new BufferedOutputStream(outs);    
                response.setContentType("text/plain");
                response.setHeader(    
                        "Content-disposition",
                        "attachment;filename="
                                + URLEncoder.encode(file.getName(), "UTF-8")); 
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }    
                bouts.flush();//     
                ins.close();    
                bins.close();    
                outs.close();    
                bouts.close();    
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
	}	
	
	@RequestMapping(value="/register.do", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "wechatid", required = true) String wechatid,
			@RequestParam(value = "wechatcode", required = false, defaultValue = "") String wechatcode) throws Exception {
		 
		ModelAndView mv = new ModelAndView("wechat/register");
		
		mv.addObject("wechatid", wechatid + "idandcode" + wechatcode);

		return mv;
	}
	
	@RequestMapping(value="/register.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "attendYear", required = true) int attendYear,
			@RequestParam(value = "guarder", required = true) int guarder,
			@RequestParam(value = "guarderName", required = true) String guarderName,
			@RequestParam(value = "guarderPhone", required = true) String guarderPhone,
			@RequestParam(value = "note", required = false, defaultValue="") String note,
			@RequestParam(value = "school", required = false, defaultValue = "") String school,
			@RequestParam(value = "scoreLevel", required = false, defaultValue = "0") int scoreLevel
			) throws Exception { 
        
        studentInfoService.saveStudentInfo(studentId, name, sex, attendYear, guarder, guarderName,
        		guarderPhone, note, school, scoreLevelService.getScoreLevel(scoreLevel));
        
        CommonResult cr = new CommonResult();
        cr.setResult("success");
        
        cr.setErrCode(studentId);
        
        return cr;  
    }
	
	@RequestMapping(value="/enroll.do", method = RequestMethod.GET)
	public ModelAndView enroll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "wechatid", required = true) String wechatid) throws Exception {
		
		ModelAndView mv = new ModelAndView("wechat/enroll");
		mv.addObject("wechatid", wechatid);
		
		return mv;
	}
	
	@RequestMapping(value="/search/discount.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object discount(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "studentId", required = true) String studentId) {
		
		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];
		
		int discount = wechatService.getDisCountFee(classId, studentId);
		
		Map<String, Integer> dFee = new HashMap<String, Integer>();
		dFee.put("discountFee", discount);
		
		return dFee;
	}
	
	@RequestMapping(value="/withdraw.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult withdraw(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "fee", required = true) int fee,
			@RequestParam(value = "registerChannel", required = false, defaultValue = "1") int registerChannel,
			@RequestParam(value = "payMode", required = false, defaultValue = "0") int payMode,
			@RequestParam(value = "note", required = false, defaultValue = "0") String note) throws Exception {

		CommonResult cr = new CommonResult();

		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];
		
		int result = wechatService.withdraw(classId, studentId, fee, registerChannel, payMode, note);
		
		if (result == 0) {
	        cr.setResult("success");
		}
		else if (result == -1){
	        cr.setResult("failed");
	        cr.setErrCode("您没有报过该课程");
		}
		
		return cr;
	}

	@RequestMapping(value="/enroll.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult enroll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "fee", required = true) int fee,
			@RequestParam(value = "registerChannel", required = true) int registerChannel,
			@RequestParam(value = "payMode", required = false, defaultValue = "0") int payMode,
			@RequestParam(value = "note", required = false, defaultValue = "0") String note) throws Exception {

		CommonResult cr = new CommonResult();

		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];
		
		int result = wechatService.enroll(classId, studentId, fee, registerChannel, payMode, note);
		
		if (result == 0) {
	        cr.setResult("success");
		}
		else if (result == -1){
	        cr.setResult("failed");
	        cr.setErrCode("您已经报名该课程");
		}
		else if (result == -2){
	        cr.setResult("failed");
	        cr.setErrCode("该课程已经报满");
		}
		else if (result == -3){
	        cr.setResult("failed");
	        cr.setErrCode("您的成绩不满足该课程报名要求");
		}
		else if (result == -4){
	        cr.setResult("failed");
	        cr.setErrCode("该账号尚未注册");
		}
		else if (result == -5){
	        cr.setResult("failed");
	        cr.setErrCode("该课程信息有误");
		}
		else if (result == -6){
	        cr.setResult("failed");
	        cr.setErrCode("该课程已过期");
		}
		
		return cr;
	}
	
	@RequestMapping(value="/enrollinfo.do", method = RequestMethod.GET)
	public ModelAndView enrollInfoPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = false, defaultValue = "") String studentId,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception {
		
		ModelAndView mv = new ModelAndView("wechat/enrollinfo");
		
		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];
		
		Pagination<Map> eis = registerInfoService.getRegisterInfoById(curPage, pageSize, studentId);//wechatService.getEnrollInfoByIds(curPage, pageSize, studentId);
		mv.addObject("enrollinfo", eis);
		
		return mv;
	}
	
	@RequestMapping(value="/search/enrollinfo.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object enrollInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception {
		
		String[] idandcode = studentId.split("idandcode");
		studentId = idandcode[0];

//		scoreLevelService.getScoreLevel(scoreIndex);
		
		Pagination<Map> pri = registerInfoService.getRegisterInfoById(curPage, pageSize, studentId);//wechatService.getEnrollInfoByIds(curPage, pageSize, studentId);
		
		Pagination<EnrollInfo> pei = new Pagination<EnrollInfo>();
		pei.setPageNumber(pri.getPageNumber());
		pei.setPageSize(pri.getPageSize());
		pei.setTotalElements(pri.getTotalElements());

		List<Map> result = pri.getElements();
		ScoreLevel scoreLevel = null;
		
		for (Map map : result) {
			scoreLevel = (ScoreLevel) map.get("scoreLevel");
//			scoreLevel = JSON.parseObject(, ScoreLevel.class);
			map.remove("scoreLevel");
		}
		
		EnrollInfo ei = new EnrollInfo();
		ei.setEnrollClass(pri.getElements());
		ei.setScoreLevel(scoreLevel);
		pei.setElements(ei);
		
		return pei;
	}
}
