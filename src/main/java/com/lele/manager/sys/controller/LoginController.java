package com.lele.manager.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.entity.UserCookie;
import com.lele.manager.enums.LoginStatus;
import com.lele.manager.sys.service.CookieService;
import com.lele.manager.sys.service.LoginService;
import com.lele.manager.utils.Constants;
import com.lele.manager.vo.LoginResult;
import com.lele.manager.vo.UserSession;
import com.mysql.jdbc.StringUtils;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@Autowired
	CookieService cookieService;
	
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception { 
        ModelAndView mv = new ModelAndView("sys/login");  
        return mv;  
    }

	private void afterLogin(HttpServletRequest request, HttpServletResponse response, 
												LoginResult loginResult, boolean rememberMe) {
		
		if (loginResult.getLoginStatus().equals(LoginStatus.LOGIN_SUCCESS.status())) {
			
			// remove cookie from DB
			UserCookie uc = cookieService.getUserCookieFromDB(loginResult.getUserName());
			if (uc != null) {
				cookieService.removeUserCookieFromDB(loginResult.getUserName());
			}
			
			if (rememberMe) {
				cookieService.setUserCookie(response, loginResult.getUserName());
			}
			
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession)session.getAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME);
			
			if (userSession == null || StringUtils.isNullOrEmpty(userSession.getCurURI())) {
				userSession = new UserSession();
				loginResult.setAuthUrl(request.getContextPath() + "/status/overview.do");
			}
			else {
				loginResult.setAuthUrl(request.getContextPath() + userSession.getCurURI());
			}

			userSession.setUserAccount(loginResult.getUserName());
			userSession.setUserId(loginResult.getUserId());			
			session.setAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME, userSession);
		}
		else {
			loginResult.setAuthUrl(request.getContextPath() + "/login.do");
		}
	}
	
	@RequestMapping(value = { "login.json" }, method = RequestMethod.GET)
	public @ResponseBody 
	LoginResult login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "rememberMe", required = false) boolean rememberMe) {

		LoginResult loginResult = null;
		String cookieStr = cookieService.getUserTokenFromCookie(request);
		
		if (cookieStr == null) {
			loginResult = loginService.doLogin(loginName, password);
			afterLogin(request, response, loginResult, rememberMe);
		}
		else {
			loginResult = loginService.doAutoLogin(loginName, cookieStr);
			cookieService.removeUserCookie(response, loginName, cookieStr);
			afterLogin(request, response, loginResult, rememberMe);
		}
		
		return loginResult;
	}
}
