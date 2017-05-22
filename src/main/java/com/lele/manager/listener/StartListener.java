package com.lele.manager.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lele.manager.utils.WebContext;

public class StartListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext application = sce.getServletContext();
			WebContext.context = WebApplicationContextUtils.getWebApplicationContext(application);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
