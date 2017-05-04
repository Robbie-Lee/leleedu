package com.lele.manager.utils;

import org.springframework.web.context.WebApplicationContext;

public class WebContext {
	public static WebApplicationContext context;

	public static Object getBean(String beanName) {
		if (context == null) {
			return null;
		} else {
			return context.getBean(beanName);
		}
	}
	
	public static WebApplicationContext getContext() {
		return context;
	}
}
