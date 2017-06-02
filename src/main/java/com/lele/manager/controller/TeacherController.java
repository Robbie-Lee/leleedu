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
import com.lele.manager.entity.TeacherInfo;
import com.lele.manager.service.TeacherInfoService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

	@Autowired
	TeacherInfoService teacherInfoService;

	@Auth(auth=AuthType.PAGE)
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "teacherId", required = false, defaultValue = "") String teacherId,
			@RequestParam(value = "teacherName", required = false, defaultValue = "") String teacherName,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
        ModelAndView mv = new ModelAndView("teacher/manager");
        
        Pagination<TeacherInfo> teacherInfoList = teacherInfoService.getTeacherInfoByPage(curPage, 
        		pageSize, teacherId, teacherName, sex, phone, status);
        
        mv.addObject("teacherInfo", teacherInfoList);
        
        return mv;  
    }
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/search.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "teacherId", required = false, defaultValue = "") String teacherId,
			@RequestParam(value = "teacherName", required = false, defaultValue = "") String teacherName,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
        
        return teacherInfoService.getTeacherInfoByPage(curPage, 
        						pageSize, teacherId, teacherName, sex, phone, status);
    }

	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/create.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "teacherId", required = true) String teacherId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "birthDay", required = true) String birthDay,
			@RequestParam(value = "checkInTime", required = true) String checkInTime,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "status", required = true) int status,
			@RequestParam(value = "classFeeRate", required = true) float classFeeRate,
			@RequestParam(value = "college", required = false, defaultValue = "") String college,
			@RequestParam(value = "degree", required = false, defaultValue = "") String degree,
			@RequestParam(value = "major", required = false, defaultValue = "") String major,
			@RequestParam(value = "minClassFee", required = true) int minClassFee,
			@RequestParam(value = "teachAge", required = true) int teachAge) throws Exception { 
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
        Date bDate = null;
        Date cDate = null;
        if (!Strings.isNullOrEmpty(birthDay)) {
        	bDate = sdf.parse(birthDay);
        }
        if (!Strings.isNullOrEmpty(checkInTime)) {
        	cDate = sdf.parse(checkInTime);
        }

        teacherInfoService.saveTeacherInfo(teacherId, name, sex, phone, status, bDate, cDate, 
        		classFeeRate, college, degree, major, minClassFee, teachAge);
        
        CommonResult cr = new CommonResult();
        cr.setResult("success");
        cr.setErrCode(teacherId);
        
        return cr;  
    }
}
