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
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.service.ClassCheckinService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/attend")
public class ClassAttendController extends BaseController {

	@Autowired
	ClassCheckinService classCheckinService;
	
	@Auth(auth=AuthType.PAGE, description="授课打卡页面")
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
		
        ModelAndView mv = new ModelAndView("attend/manager");
        
        Pagination<ClassInfo> classAttendList = classCheckinService.getClassAttendByPage(
        		curPage, pageSize, "", "", "", null, null);
        
        mv.addObject("classAttend", classAttendList);
        
        return mv;
	}

	@Auth(auth=AuthType.INTERFACE, description="课程查询接口")
	@RequestMapping(value="/search.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId,
			@RequestParam(value = "className", required = false, defaultValue = "") String className,
			@RequestParam(value = "teacherName", required = false, defaultValue = "") String teacherName,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
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
		
		Pagination<ClassInfo> classAttendList = classCheckinService.getClassAttendByPage(curPage, 
				pageSize, classId, className, teacherName, sDate, eDate);
		
		return classAttendList;
	}
	
	@Auth(auth=AuthType.INTERFACE, description="课程打卡接口")
	@RequestMapping(value="/checkin.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult checkin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId) throws Exception {
		
		int result = classCheckinService.checkin(classId);
		
        CommonResult cr = new CommonResult();
        if (result == 0) {
            cr.setResult("success");
            cr.setErrCode(classId);
        }
        else {
            cr.setResult("failed");
            cr.setErrCode("您今天已经打过卡了");
        }
        
        return cr;  
	}
}
