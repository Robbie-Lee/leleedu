package com.lele.manager.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.dao.RegisterInfoDAO;
import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.service.RegisterInfoService;
import com.lele.manager.sys.dao.Pagination;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

	@Autowired
	RegisterInfoService registerInfoService;
	
	@Auth(auth=AuthType.PAGE)
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("register/manager");
		Pagination<RegisterInfo> riPage = 
				registerInfoService.getRegisterInfoByPage(0, 20, "", null, null, null);
		
		mv.addObject("registerInfo", riPage);
		
		return mv;
	}
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/search/registerinfo.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object enrollInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "className", required = false, defaultValue = "") String className,
			@RequestParam(value = "studentName", required = false, defaultValue = "") String studentName,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endtDate", required = false, defaultValue = "") String endDate,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	       
        Date sDate = null;
        Date eDate = null;
        if (!Strings.isNullOrEmpty(startDate)) {
        	sDate = sdf.parse(startDate);
        }
        if (!Strings.isNullOrEmpty(endDate)) {
        	eDate = sdf.parse(endDate);
        }
        
		Pagination<RegisterInfo> riPage = 
				registerInfoService.getRegisterInfoByPage(curPage, pageSize, className, studentName, sDate, eDate);
		
		return riPage;

	}	
}
