package com.lele.manager.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.google.common.base.Strings;
import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.sys.dao.UserDAO;
import com.lele.manager.sys.entity.Resource;
import com.lele.manager.sys.entity.Role;
import com.lele.manager.sys.entity.User;
import com.lele.manager.sys.service.ResourceService;
import com.lele.manager.utils.WebContext;

public class SecurityHolder {
	//	account : set<resource>
	private static Map<String, Set<AuthResource>> sysAuthority = new HashMap<String, Set<AuthResource>>();
	
	public static boolean isAuthorized(String userName, AuthResource resource) {
		
		Set<AuthResource> resourceSet = sysAuthority.get(userName);
		
		if (resourceSet == null){
			return false;
		}
		
		if (resourceSet.contains(resource)) {
			return true;
		}
		
		return false;
	}
	
	public static void scanRequestMapping() {
		Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(WebContext.context, 
				HandlerMapping.class, true, false);
		
		for (String key : allRequestMappings.keySet()) {
			if (allRequestMappings.get(key) instanceof RequestMappingHandlerMapping) {
				
				RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping)allRequestMappings.get(key);
				
				Map<RequestMappingInfo, HandlerMethod>  map = handlerMapping.getHandlerMethods();
		        Iterator<Map.Entry<RequestMappingInfo, HandlerMethod>> it = map.entrySet().iterator();
		        
		        ResourceService resourceService = (ResourceService) WebContext.getBean("resourceService");
		        List<Resource> resourceList = new ArrayList<Resource>();
		        
		        while(it.hasNext()){
		            Map.Entry<RequestMappingInfo, HandlerMethod> entry = it.next();
		            RequestMappingInfo rm = entry.getKey();
		            HandlerMethod hm = entry.getValue();
		            
		    		Auth annoAuth = hm.getMethod().getAnnotation(Auth.class);
		    		
    				if (annoAuth != null) {
    					//	write db
    					if (annoAuth.auth() == AuthType.INTERFACE || annoAuth.auth() == AuthType.PAGE) {
    						
    						Resource resource = new Resource();

    						resource = new Resource();
    						resource.setAction("");
    						resource.setDescription(annoAuth.description());
    						resource.setMenuUrl(rm.getPatternsCondition().toString().replace("[", "").replace("]", ""));
    						resource.setResType(annoAuth.auth().getIndex());

    						resourceList.add(resource);
    					}
    				}
    				else {
    		            System.out.println(rm.getPatternsCondition().toString().replace("[", "").replace("]", ""));
    				}
		        }
		        
		        for (Resource resource : resourceList) {
		        	boolean flag = false;
		        	List<Resource> resInDB = resourceService.getResource(resource.getMenuUrl());

		        	if (resInDB == null) {
		        		resourceService.saveResource(resource);
		        	}
		        	else {
			        	for (Resource res : resInDB) {
			        		if (Strings.isNullOrEmpty(res.getAction())) {
				        		res.setDescription(resource.getDescription());
				        		res.setResType(resource.getResType());
				        		resourceService.saveResource(res);
				        		flag = true;
			        		}
			        	}
			        	
			        	if (flag == false) {
			        		resourceService.saveResource(resource);
			        	}
		        	}
		        }
			}
		}
	}
	
	public static void loadSysAuthority() {

		UserDAO userDao = (UserDAO) WebContext.getBean("userDAO");

		sysAuthority.clear();

		List<User> userList = userDao.findAll();
		
		for (User user : userList) {
			
			if (!user.getEnable()) {
				continue;
			}
			
			String account = user.getAccount();
			Set<Role> roleSet = user.getRole();
			
			Set<AuthResource> authResSet = new HashSet<AuthResource>();
			for (Role role : roleSet) {
				
				if (!role.getEnable()) {
					continue;
				}
				
				Set<Resource> resourceSet = role.getResource();
				
				for (Resource resource : resourceSet) {
					
					AuthResource ar = new AuthResource(resource.getMenuUrl(), resource.getAction());
					authResSet.add(ar);
				}
			}
			
			sysAuthority.put(account, authResSet);
		}
	}
}
