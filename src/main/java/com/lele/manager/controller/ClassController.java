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
import com.lele.manager.entity.ClassInfo;
import com.lele.manager.service.ClassInfoService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/class")
public class ClassController extends BaseController {

	@Autowired
	ClassInfoService classInfoService;
	
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId,
			@RequestParam(value = "className", required = false, defaultValue = "") String className,
			@RequestParam(value = "teacherName", required = false, defaultValue = "") String teacherName,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
			@RequestParam(value = "scoreLevel", required = false, defaultValue = "0") int scoreLevel,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
        ModelAndView mv = new ModelAndView("class/manager");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
        Date sDate = null;
        Date eDate = null;
        if (!Strings.isNullOrEmpty(startDate)) {
        	sDate = sdf.parse(startDate);
        }
        if (!Strings.isNullOrEmpty(endDate)) {
        	eDate = sdf.parse(endDate);
        }
        
        Pagination<ClassInfo> classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
        		classId, className, teacherName, sDate, eDate, scoreLevel);
        
        mv.addObject("classInfo", classInfoList);
        
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
			@RequestParam(value = "scoreLevel", required = false, defaultValue = "0") int scoreLevel,
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
        
        Pagination<ClassInfo> classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
        		classId, className, teacherName, sDate, eDate, scoreLevel);
        
        return classInfoList;  
    }

	@RequestMapping(value="/create.json", method = RequestMethod.GET)
	public @ResponseBody 
	CommonResult create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId,
			@RequestParam(value = "className", required = false, defaultValue = "") String className,
			@RequestParam(value = "teacherName", required = false, defaultValue = "") String teacherName,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
			@RequestParam(value = "scoreLevel", required = false, defaultValue = "0") int scoreLevel,
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
        
        Pagination<ClassInfo> classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
        		classId, className, teacherName, sDate, eDate, scoreLevel);
        
        return null;  
    }
	
}
