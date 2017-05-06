package com.lele.manager.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lele.manager.sys.dao.UserDAO;
import com.lele.manager.sys.entity.Resource;
import com.lele.manager.sys.entity.Role;
import com.lele.manager.sys.entity.User;
import com.lele.manager.utils.WebContext;

public class SecurityHolder {

	//	account : set<resource>
	private static Map<String, Set<String>> sysAuthority = new HashMap<String, Set<String>>();
	
	public static boolean isAuthorized(String userName, String resource) {
		
		Set<String> resourceSet = sysAuthority.get(userName);
		
		if (resourceSet == null){
			return false;
		}
		
		if (resourceSet.contains(resource)) {
			return true;
		}
		
		return false;
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
			
			Set<String> urlSet = new HashSet<String>();
			for (Role role : roleSet) {
				
				if (!role.getEnable()) {
					continue;
				}
				
				Set<Resource> resourceSet = role.getResource();
				
				for (Resource resource : resourceSet) {
					urlSet.add(resource.getResource());
				}
			}
			
			sysAuthority.put(account, urlSet);
		}
	}
}
