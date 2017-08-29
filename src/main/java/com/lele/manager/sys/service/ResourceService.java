package com.lele.manager.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.sys.dao.ResourceDAO;
import com.lele.manager.sys.entity.Resource;

@Service("resourceService")
public class ResourceService {

	private final String HQL_ENTITY = "Resource";
	
	@Autowired
	ResourceDAO resourceDao;
	
	public List<Resource> getResource(String menuUrl) {
		final String hql = "from " + HQL_ENTITY + " where menuUrl = ?0";
		return resourceDao.doQueryList(hql, menuUrl);
	}
	
	public void saveResource(Resource resource) {
		resourceDao.save(resource);
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
	public List<Resource> getResourceList() {
		return resourceDao.findAll();
	}
	
	public Pagination<Resource> getResourceByPage(int curPage, int pageSize) {
		final String hql = "from " + HQL_ENTITY;
		return resourceDao.doQuery(hql, curPage, pageSize);
	}
}
