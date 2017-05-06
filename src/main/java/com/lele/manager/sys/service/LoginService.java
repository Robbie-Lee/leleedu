package com.lele.manager.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.enums.LoginStatus;
import com.lele.manager.sys.dao.UserCookieDAO;
import com.lele.manager.sys.dao.UserDAO;
import com.lele.manager.sys.entity.User;
import com.lele.manager.sys.entity.UserCookie;
import com.lele.manager.utils.AES;
import com.lele.manager.vo.LoginResult;

@Service("loginService")
public class LoginService {

	@Autowired
	UserDAO userDao;
	
	@Autowired
	CookieService cookieService;
	
	public LoginResult doLogin(String userName, String password) {
		
		LoginResult loginResult = new LoginResult();
		loginResult.setResultFlag(1);
		
		User user = userDao.getUserByName(userName);
		
		if (user == null) {
			loginResult.setLoginStatus(LoginStatus.USER_NOT_EXIST.status());
			return loginResult;
		}
		else if (user.getEnable() == false) {
			loginResult.setLoginStatus(LoginStatus.USER_DISABLED.status());
			return loginResult;
		}
		
		if (userDao.verifyPassword(user.getId(), user.getAccount(), password)) {
			loginResult.setLoginStatus(LoginStatus.LOGIN_SUCCESS.status());
			loginResult.setUserId(user.getId());
			loginResult.setUserName(userName);
			loginResult.setResultFlag(0);
		}
		else {
			loginResult.setLoginStatus(LoginStatus.USER_NOT_EXIST.status());
		}
		
		return loginResult;
	}
	
	public LoginResult doAutoLogin(String loginName, String cookieStr) {

		LoginResult loginResult = new LoginResult();
		loginResult.setResultFlag(1);

		User user = userDao.getUserByName(loginName);
		
		if (cookieService.verifyCookie(loginName, cookieStr)) {
			loginResult.setLoginStatus(LoginStatus.LOGIN_SUCCESS.status());
			loginResult.setUserId(user.getId());
			loginResult.setUserName(loginName);
			loginResult.setResultFlag(0);
		}
		else {
			loginResult.setLoginStatus(LoginStatus.AUTO_LOGIN_FAILED.status());
		}
		
		return loginResult;
	}
}
