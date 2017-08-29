package com.lele.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.sys.entity.Resource;
import com.lele.manager.sys.service.ResourceService;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

	@Autowired
	ResourceService resourceService;
	
	@Auth(auth=AuthType.PAGE, description="资源管理页面")
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response) {
	
		ModelAndView mv = new ModelAndView("resource/manager");
		Pagination<Resource> resourcePage = resourceService.getResourceByPage(0, 20);
		
		mv.addObject("resourceInfo", resourcePage);
		return mv;
	}
	
	@Auth(auth=AuthType.INTERFACE, description="资源查询接口")
	@RequestMapping(value="/resource.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object resource(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) {
	
		return resourceService.getResourceByPage(curPage, pageSize);
	}
	
	@Auth(auth=AuthType.INTERFACE, description="资源获取接口")
	@RequestMapping(value={"resourcelist.json"}, method = RequestMethod.GET)
	public @ResponseBody 
	List<Resource> getResourceList() {
		List<Resource> resourceList = resourceService.getResourceList();
		return resourceList;
	}
}
