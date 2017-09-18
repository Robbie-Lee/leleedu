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
import com.lele.manager.entity.TeacherInfo;
import com.lele.manager.service.ClassInfoService;
import com.lele.manager.service.ScoreLevelService;
import com.lele.manager.service.TeacherInfoService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.sys.entity.Role;
import com.lele.manager.sys.entity.User;
import com.lele.manager.sys.service.UserService;
import com.lele.manager.utils.CommonResult;
import com.lele.manager.utils.Constants;
import com.lele.manager.vo.UserSession;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/class")
public class ClassController extends BaseController {

	@Autowired
	ClassInfoService classInfoService;

	@Autowired
	TeacherInfoService teacherInfoService;

	@Autowired
	ScoreLevelService scoreLevelService;
	
	@Autowired
	UserService userService;

	@Auth(auth=AuthType.PAGE, description="课程管理页面")
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId,
			@RequestParam(value = "className", required = false, defaultValue = "") String className,
			@RequestParam(value = "classGrade", required = false, defaultValue = "0") int classGrade,
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
        
        UserSession us = (UserSession)request.getSession().getAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME);
        User user = us.getUser();
        
        boolean isAdmin = false;
        for (Role role : user.getRole()) {
        	if (role.getName().contains("管理员")) {
        		isAdmin = true;
        	}
        }
        
        Pagination<ClassInfo> classInfoList = null;
        
        if (isAdmin) {
            classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
            		classId, className, teacherName, sDate, eDate, scoreLevel, classGrade);
        }
        else {
            classInfoList = classInfoService.getClassInfoByTeacherId(curPage, pageSize, 
            		classId, className, user.getTeacherId(), sDate, eDate, scoreLevel, classGrade);
        }
        
        mv.addObject("classInfo", classInfoList);
        
        return mv;  
    }

	@Auth(auth=AuthType.INTERFACE, description="查看报名学生接口")
	@RequestMapping(value="/register.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object getRegister(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = true) String classId) throws Exception { 

		return classInfoService.getStudentByClassId(classId);
	}
	
	@Auth(auth=AuthType.INTERFACE, description="课程搜索接口")
	@RequestMapping(value="/search.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = false, defaultValue = "") String classId,
			@RequestParam(value = "className", required = false, defaultValue = "") String className,
			@RequestParam(value = "classGrade", required = false, defaultValue = "0") int classGrade,
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
		
        UserSession us = (UserSession)request.getSession().getAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME);
        User user = us.getUser();
		
        boolean isAdmin = false;
        for (Role role : user.getRole()) {
        	if (role.getName().contains("管理员")) {
        		isAdmin = true;
        	}
        }
        
        Pagination<ClassInfo> classInfoList = null;
        if (isAdmin) {
            classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
            		classId, className, teacherName, sDate, eDate, scoreLevel, classGrade);
        }
        else if (!StringUtils.isNullOrEmpty(teacherName)){
        	TeacherInfo tinfo = teacherInfoService.getTeacherInfoByKeyId(user.getTeacherId());
        	
        	if (tinfo.getName().equals(teacherName)) {
                classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
                		classId, className, teacherName, sDate, eDate, scoreLevel, classGrade);
            }
        	else {
                classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
                		classId, className, "other", sDate, eDate, scoreLevel, classGrade);
        	}
        }
        else {
        	TeacherInfo tinfo = teacherInfoService.getTeacherInfoByKeyId(user.getTeacherId());

            classInfoList = classInfoService.getClassInfoByPage(curPage, pageSize, 
            		classId, className, tinfo.getName(), sDate, eDate, scoreLevel, classGrade);
        }
        
        return classInfoList;  
    }

	@Auth(auth=AuthType.INTERFACE, description="课程创建接口")
	@RequestMapping(value="/create.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "className", required = true) String className,
			@RequestParam(value = "classGrade", required = false, defaultValue = "0") int classGrade,
			@RequestParam(value = "classRoom", required = true) String classRoom,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "classTime", required = true) String classTime,
			@RequestParam(value = "teacherName", required = true) String teacherName,
			@RequestParam(value = "teacherId", required = true) String teacherId,
			@RequestParam(value = "classCount", required = true) int classCount,
			@RequestParam(value = "registerLimit", required = true) int registerLimit,
			@RequestParam(value = "classPrice", required = true) int classPrice,
			@RequestParam(value = "acceptDiscount", required = true) boolean acceptDiscount,
			@RequestParam(value = "classDescription", required = false, defaultValue = "") String classDescription,
			@RequestParam(value = "scoreLevel", required = true) int scoreLevel) throws Exception { 
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
        Date sDate = null;
        Date eDate = null;
        if (!Strings.isNullOrEmpty(startDate)) {
        	sDate = sdf.parse(startDate);
        }
        if (!Strings.isNullOrEmpty(endDate)) {
        	eDate = sdf.parse(endDate);
        }
        
        UserSession us = (UserSession)request.getSession().getAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME);
        User user = us.getUser();
		
        boolean isAdmin = false;
        for (Role role : user.getRole()) {
        	if (role.getName().contains("管理员")) {
        		isAdmin = true;
        	}
        }
        
        CommonResult cr = new CommonResult();

        if (isAdmin) {
            classInfoService.saveClassInfo(classId, 
            		className, classRoom, sDate, eDate, classTime, teacherName, teacherId,
            		classCount, classPrice, acceptDiscount, classDescription,
            		scoreLevelService.getScoreLevel(scoreLevel), classGrade, registerLimit);
            
            cr.setResult("success");
            cr.setErrCode(classId);
        }
        else {
        	TeacherInfo tinfo = teacherInfoService.getTeacherInfoByKeyId(user.getTeacherId());
        	
        	if (tinfo.getName().equals(teacherName)) {
                classInfoService.saveClassInfo(classId, 
                		className, classRoom, sDate, eDate, classTime, teacherName, teacherId,
                		classCount, classPrice, acceptDiscount, classDescription,
                		scoreLevelService.getScoreLevel(scoreLevel), classGrade, registerLimit);
                
                cr.setResult("success");
                cr.setErrCode(classId);
            }
        	else {
                cr.setResult("failed");
                cr.setErrCode(classId);
        	}
        }
        
        return cr;  
    }
	
	@Auth(auth=AuthType.INTERFACE, description="课程激活/关闭接口")
	@RequestMapping(value="/active.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "active", required = false, defaultValue = "true") boolean active) {

		classInfoService.activeClass(classId, active);
		
        CommonResult cr = new CommonResult();
        cr.setResult("success");
        cr.setErrCode(classId);
        
        return cr;  		
	}
}
