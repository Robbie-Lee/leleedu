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
import com.lele.manager.entity.ClassAttend;
import com.lele.manager.service.ClassAttendService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/attend")
public class ClassAttendController extends BaseController {

	@Autowired
	ClassAttendService classAttendService;
	
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
		
        ModelAndView mv = new ModelAndView("attend/manager");
        
        Pagination<ClassAttend> classAttendList = classAttendService.getClassAttendByPage(curPage, pageSize, "", "", "", null, null);
        
        mv.addObject("classAttend", classAttendList);
        
        return mv;
	}
	
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
		
		Pagination<ClassAttend> classAttendList = classAttendService.getClassAttendByPage(curPage, 
				pageSize, classId, className, teacherName, sDate, eDate);
		
		return classAttendList;
	}
	
	@RequestMapping(value="/checkin.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult checkin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId) throws Exception {
		
		classAttendService.checkIn(classId);
		
        CommonResult cr = new CommonResult();
        cr.setResult("success");
        cr.setErrCode(classId);
        
        return cr;  
	}
}
