package com.lele.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.entity.StudentInfo;
import com.lele.manager.service.DiscountService;
import com.lele.manager.service.ScoreLevelService;
import com.lele.manager.service.StudentInfoService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {
	@Autowired
	StudentInfoService studentInfoService;

	@Autowired
	ScoreLevelService scoreLevelService;
	
	@Autowired
	DiscountService discountService;
	
	private Pagination<StudentInfo> getStudentInfo(int curPage, int pageSize, 
			String studentId, String studentName, String sex, int grade,
			String guarderName, String guarderPhone) {
		
		Pagination<StudentInfo> studentInfoList = studentInfoService.getStudentInfoByPage(curPage, pageSize, 
				studentId, studentName, sex, grade, guarderName, guarderPhone);
		
		for (StudentInfo si : studentInfoList.getElements()) {
			si.setDiscountRate(discountService.getDiscountRate(si.getTotalFee()));
		}
		
		return studentInfoList;
	}
	
	@Auth(auth=AuthType.PAGE)
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = false, defaultValue = "") String studentId,
			@RequestParam(value = "studentName", required = false, defaultValue = "") String studentName,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "grade", required = false, defaultValue = "-1") int grade,
			@RequestParam(value = "guarderName", required = false, defaultValue = "") String guarderName,
			@RequestParam(value = "guarderPhone", required = false, defaultValue = "") String guarderPhone,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
        
		ModelAndView mv = new ModelAndView("student/manager");
        
        Pagination<StudentInfo> studentInfoList = getStudentInfo(curPage, pageSize, 
        				studentId, studentName, sex, grade, guarderName, guarderPhone);
        
        mv.addObject("studentInfo", studentInfoList);
        
        return mv;  
    }

	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/score.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult score(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "classIds", required = false, defaultValue = "") String classIds,
			@RequestParam(value = "classScores", required = false, defaultValue = "") String classScores,
			@RequestParam(value = "scoreLevel", required = false, defaultValue = "0") int scoreLevel) throws Exception { 

		studentInfoService.updateStudentScore(studentId, classIds, classScores, scoreLevel);
		
        CommonResult cr = new CommonResult();
        cr.setResult("success");
        cr.setErrCode(studentId);
        
        return cr;  
    }
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/search.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = false, defaultValue = "") String studentId,
			@RequestParam(value = "studentName", required = false, defaultValue = "") String studentName,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "grade", required = false, defaultValue = "-1") int grade,
			@RequestParam(value = "guarderName", required = false, defaultValue = "") String guarderName,
			@RequestParam(value = "guarderPhone", required = false, defaultValue = "") String guarderPhone,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) throws Exception { 
        
        return getStudentInfo(curPage, pageSize, 
				studentId, studentName, sex, grade, guarderName, guarderPhone);
    }

	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value="/create.json", method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "grade", required = true) int grade,
			@RequestParam(value = "guarder", required = true) int guarder,
			@RequestParam(value = "guarderName", required = true) String guarderName,
			@RequestParam(value = "guarderPhone", required = true) String guarderPhone,
			@RequestParam(value = "note", required = false, defaultValue="") String note,
			@RequestParam(value = "school", required = false, defaultValue = "") String school,
			@RequestParam(value = "scoreLevel", required = false, defaultValue = "0") int scoreLevel
			) throws Exception { 
        
        studentInfoService.saveStudentInfo(studentId, name, sex, grade, guarder, guarderName,
        		guarderPhone, note, school, scoreLevelService.getScoreLevel(scoreLevel));
        
        CommonResult cr = new CommonResult();
        cr.setResult("success");
        cr.setErrCode(studentId);
        
        return cr;  
    }
}
