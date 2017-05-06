package com.lele.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.service.ClassInfoService;

@Controller
@RequestMapping("/class")
public class ClassController extends BaseController {

	@Autowired
	ClassInfoService classInfoService;
	
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception { 
        ModelAndView mv = new ModelAndView("class/manager");
//        classInfoService.getClassInfoByPage(curPage, pageSize, classId, className, teacherName, startDate, endDate, scoreLevel)
        return mv;  
    }

}
