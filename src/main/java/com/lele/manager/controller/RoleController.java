package com.lele.manager.controller;

import java.util.Date;
import java.util.Set;

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
import com.lele.manager.security.SecurityHolder;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.sys.entity.Resource;
import com.lele.manager.sys.entity.Role;
import com.lele.manager.sys.service.ResourceService;
import com.lele.manager.sys.service.RoleService;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	@Autowired
	RoleService roleService;

	@Autowired
	ResourceService resourceService;

	@Auth(auth=AuthType.PAGE, description="角色管理页面")
	@RequestMapping(value="/manager.do", method = RequestMethod.GET)
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response) {
	
		ModelAndView mv = new ModelAndView("role/manager");
		Pagination<Role> rolePage = roleService.getRoleByPage(0, 20);
		
		mv.addObject("roleInfo", rolePage);
		return mv;
	}
	
	@Auth(auth=AuthType.INTERFACE, description="角色查询接口")
	@RequestMapping(value="/role.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object role(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage) {
	
		return roleService.getRoleByPage(curPage, pageSize);
	}	

	@Auth(auth=AuthType.INTERFACE, description="角色获取接口")
	@RequestMapping(value="/rolelist.json", method = RequestMethod.GET)
	public @ResponseBody 
	Object rolelist(HttpServletRequest request, HttpServletResponse response) {
	
		return roleService.getRoleList();
	}	
	
	@Auth(auth=AuthType.INTERFACE, description="新增角色接口")
	@RequestMapping(value="/saverole.json", method = RequestMethod.POST)
	public @ResponseBody 
	Object save(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = false, defaultValue = "") String description,
			@RequestParam(value = "enable", required = true) boolean enable,
			@RequestParam(value = "resourceId", required = true) String[] resourceId) {
		
		Set<Resource> resourceSet = resourceService.getResourceSetByIds(resourceId);
		
		Date now = new Date();
		
		Role role = new Role();
		role.setCreateTime(now);
		role.setModifyTime(now);
		role.setDescription(description);
		role.setEnable(enable);
		role.setName(name);
		role.setResource(resourceSet);
		
		roleService.saveRole(role);
		SecurityHolder.loadSysAuthority();
		
		CommonResult cr = new CommonResult();
		cr.setResult(CommonResult.SUCCESS);
		return cr;
	}
}
