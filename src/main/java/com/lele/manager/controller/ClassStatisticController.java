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
import com.lele.manager.entity.ClassStatistic;
import com.lele.manager.service.ClassStatisticService;
import com.lele.manager.sys.dao.Pagination;

@Controller
@RequestMapping("/statistic")
public class ClassStatisticController {

	@Autowired
	ClassStatisticService classStatisticService;
	
	@Auth(auth=AuthType.PAGE)
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
		
        ModelAndView mv = new ModelAndView("statistic/manager");
        
        Pagination<ClassStatistic> classStatisticPage = classStatisticService.calcTeacherSalary(curPage, pageSize, "", null, null);
        
        mv.addObject("classStatistic", classStatisticPage);
        
        return mv;
	}
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/search.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object search(HttpServletRequest request, HttpServletResponse response,
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
        
        return classStatisticService.calcTeacherSalary(curPage, pageSize, teacherName, sDate, eDate);
	}
}
