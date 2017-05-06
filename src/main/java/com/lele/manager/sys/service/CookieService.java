package com.lele.manager.sys.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lele.manager.sys.dao.UserCookieDAO;
import com.lele.manager.sys.entity.UserCookie;
import com.lele.manager.utils.AES;
import com.lele.manager.utils.Constants;
import com.lele.manager.utils.CookieUtils;

@Service("cookieService")
public class CookieService {

	@Autowired
	UserCookieDAO userCookieDao;
	
	public String getUserTokenFromCookie(HttpServletRequest request) {
		return CookieUtils.getCookie(request, Constants.DEFAULT_COOKIE_TOKEN);
	}
	
	public UserCookie getUserCookieFromDB(String userName) {
		return userCookieDao.getCookieByUserName(userName);
	}
	
	public void removeUserCookieFromDB(String userName) {
		userCookieDao.removeCookieByUserName(userName);
	}
	
	public void setUserCookie(HttpServletResponse response, String userName) {

		Date date = new Date();
		String cookieToken = AES.AESEncrypt(userName + "&" + date.toString());

		UserCookie uc = new UserCookie();
		uc.setUserName(userName);
		uc.setLastLoginTime(date);
		uc.setUserToken(cookieToken);

		CookieUtils.addCookie(Constants.DEFAULT_COOKIE_TOKEN, cookieToken, response);
		CookieUtils.addCookie(Constants.DEFAULT_COOKIE_LOGIN_NAME, userName, response);
		
		userCookieDao.save(uc);
	}
	
	public void removeUserCookie(HttpServletResponse response, String loginName, String cookieStr) {
		CookieUtils.removeCookie(Constants.DEFAULT_COOKIE_TOKEN, cookieStr, response);
		CookieUtils.removeCookie(Constants.DEFAULT_COOKIE_LOGIN_NAME, loginName, response);
		userCookieDao.removeCookieByUserName(loginName);
	}
	
	public boolean verifyCookie(String loginName, String cookieStr) {
	
		UserCookie ucFromDB = userCookieDao.getCookieByUserName(loginName);
		
		if (ucFromDB == null) {
			return false;
		}
		
		String[] tokenFromBrowser = parseUserCookie(cookieStr).split("&");
		
		if (ucFromDB.getUserToken().equals(cookieStr) && loginName.equals(tokenFromBrowser[0])) {
			return true;
		}
		
		return false;
	}
	
	public String parseUserCookie(String cookieStr) {
		
		return AES.AESDecrypt(cookieStr);
	}
}
