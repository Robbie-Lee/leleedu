package com.lele.manager.aspect;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.utils.CommonResult;
import com.lele.manager.controller.BaseController;
import com.lele.manager.sys.service.CookieService;
import com.lele.manager.enums.SysInfo;
import com.lele.manager.utils.Constants;
import com.lele.manager.vo.SysMenu;
import com.lele.manager.vo.UserSession;
import com.mysql.jdbc.StringUtils;

@Aspect
@Component
public class PrivilegeAspect {

	@Autowired
	CookieService cookieService;
	
	@Pointcut("within(com.lele.manager.controller.*)")
	public void aspect(){}
	
	@Around("aspect() && @annotation(com.lele.manager.annotation.Auth)")
	public Object aroundDoPrivilege(ProceedingJoinPoint pjp) {
	
		HttpServletRequest request = BaseController.request();
		HttpServletResponse response = BaseController.response();
		
		Object result = null;
		UserSession us = null;
		if (request != null) {
			us = (UserSession)request.getSession().getAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME);
		}
		
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Auth annoAuth = ms.getMethod().getAnnotation(Auth.class);
		
		if (us == null || StringUtils.isNullOrEmpty(us.getUserAccount()) || us.getUserId() <= 0) {
			try {
				if (annoAuth.auth() == AuthType.PAGE) {
					UserSession userSession = new UserSession();
					
					userSession.setCurURI(request.getRequestURI().
							substring(request.getContextPath().length(), request.getRequestURI().length()));
					
					HttpSession session = request.getSession();
					session.setAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME, userSession);
					
					response.sendRedirect(request.getContextPath() + "/login.do");
					return null;
				}
				else {
					CommonResult cr = new CommonResult();
					cr.setResult(CommonResult.FAILED);
					cr.setErrCode(SysInfo.NOT_LOGININ_USER.info());
					response.getWriter().write(cr.toString());
					return cr;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String requestURI = request.getRequestURI().
				substring(request.getContextPath().length(), request.getRequestURI().length());

		try {
			if (annoAuth.auth() == AuthType.PAGE) {
				us.setCurURI(request.getRequestURI());
			}

			HttpSession session = request.getSession();
			session.setAttribute(Constants.DEFAULT_SESSION_ATTRIBUTE_NAME, us);

			result = pjp.proceed();
				
			if (annoAuth.auth() == AuthType.PAGE) {
				if (result instanceof ModelAndView) {
					((ModelAndView) result).addObject(Constants.CUR_USER, us.getUserAccount());
						
					String[] uriSplit = requestURI.split("/");
						
					if(uriSplit != null && uriSplit.length > 0){
						SysMenu menu = new SysMenu();
					
						menu.setLevel1(uriSplit[1]);
						if(uriSplit.length > 2){
							menu.setLevel2(uriSplit[2]);
						}

						((ModelAndView) result).addObject(Constants.CUR_MENU, menu);
					}
				}
			}
				
			return result;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
