package com.lele.manager.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.sys.dao.ResourceDAO;
import com.lele.manager.sys.entity.Resource;

@Service("resourceService")
public class ResourceService {

	@Autowired
	ResourceDAO resourceDao;
	
	public List<Resource> getResourceList() {
		return resourceDao.findAll();
	}
	
	public Set<Resource> getResourceSetByIds(String[] resourceIds) {
/*		
		if (Strings.isNullOrEmpty(resourceIds)) {
			return null;
		}

		String[] ids = resourceIds.split(",");
*/
		Set<Resource> resourceSet = new HashSet<Resource>();
		for (String id : resourceIds) {
			resourceSet.add(resourceDao.getById(Long.valueOf(id)));
		}
		
		return resourceSet;
	}
}
