package com.lele.manager.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.sys.service.CookieService;
import com.lele.manager.utils.Constants;

@Controller
public class LogoutController {

	@Autowired
	CookieService cookieService;
	
	@RequestMapping(value="/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME);
        ModelAndView mv = new ModelAndView("sys/login");  
        return mv;  
    }
}
