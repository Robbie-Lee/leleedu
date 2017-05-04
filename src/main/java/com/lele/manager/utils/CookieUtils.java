package com.lele.manager.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {


    /**
     * @param name 
     * @param value 
     * @param domain domain
     * ＠param  path path 
     * @param maxage  
     * @param response
     */
    public static void addCookie(String name, String value, String domain, int maxage,
    											String path, HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        if(domain!=null){
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxage);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
     
    /**
     * * @param name 
     * @param value 
     * @param domain domain
     * @param maxage  
     * @param response
     */
    public static void addCookie(String name, String value, HttpServletResponse response){
        addCookie(name, value, Constants.DEFAULT_COOKIE_DOMAIN, 
        		Constants.DEFAULT_COOKIE_MAXAGE, Constants.DEFAULT_COOKIE_PATH, response);
    }
    
    /**
     * @param req
     * @param name
     * @return cookie
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }
    
    public static void removeCookie(String name, String value, HttpServletResponse response) {
    	addCookie(name, null, Constants.DEFAULT_COOKIE_DOMAIN, 0, Constants.DEFAULT_COOKIE_PATH, response);
    }
 
    public static void removeCookie(String name, String domain, String path, 
    						HttpServletRequest request, HttpServletResponse response) {
        String cookieVal = getCookie(request,name);
        if(cookieVal!=null){
            addCookie(name, null, domain, 0, path, response);
        }
    }
 
    public static void removeCookie(String name, HttpServletRequest request, 
    												HttpServletResponse response) {
       removeCookie(name, Constants.DEFAULT_COOKIE_DOMAIN, Constants.DEFAULT_COOKIE_PATH, request, response);
    }
}
