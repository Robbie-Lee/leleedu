package com.lele.manager.controller;

import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.sys.entity.User;
import com.lele.manager.sys.service.UserService;
import com.lele.manager.utils.AES;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/manager")
public class ManagerController extends BaseController {

	@Autowired
	UserService userService;
	
	private final static String USER_LIST = "userlist";
	
	@Auth(auth=AuthType.PAGE)
	@RequestMapping(value="/user.do", method = RequestMethod.GET)
	public ModelAndView user(HttpServletRequest request, HttpServletResponse response) throws Exception {  

		List<User> userList = userService.getUserList();
		
		for (User user : userList) {
			user.setPassword(null);
		}
		
		System.out.println(JSON.toJSONString(userList));
		
        ModelAndView mv = new ModelAndView("user/manager");
        mv.addObject(USER_LIST, userList);
        return mv;  
    }

	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value={"searchuser.json"}, method = RequestMethod.GET)
	public @ResponseBody 
	Object searchUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName) throws Exception {  

		return userService.getUser(userName);
    }
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value={"activeuser.json"}, method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult activeUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userId", required = true) int userId,
			@RequestParam(value = "active", required = true) boolean active) throws Exception {  

		userService.activeUser(userId, active);
		
		CommonResult cr = new CommonResult();
		cr.setResult(CommonResult.SUCCESS);
		return cr;
    }
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value={"changepassword.json"}, method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult changePassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userId", required = true) int userId,
			@RequestParam(value = "password", required = true) String password) throws Exception {  
		userService.changePassword(userId, password);
		
		CommonResult cr = new CommonResult();
		cr.setResult(CommonResult.SUCCESS);
		return cr;
    }
	
	@Auth(auth=AuthType.INTERFACE)
	@RequestMapping(value={"adduser.json"}, method = RequestMethod.POST)
	public @ResponseBody 
	CommonResult addUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "account", required = true) String account,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "email", required = false, defaultValue="") String email,
			@RequestParam(value = "phone", required = false, defaultValue="") String phone,
			@RequestParam(value = "active", required = false, defaultValue="") boolean active) {

		User user = userService.getUser(account);
		if (user == null) {
			user = new User();
		}
		
		user.setAccount(account);
		user.setCreateTime(new Date());
		user.setEmail(email);
		user.setEnable(active);
		user.setModifyTime(new Date());
		user.setName(name);
		user.setPassword(AES.AESEncrypt(password));
		user.setPhone(phone);
		
		userService.addUser(user);
		
		CommonResult cr = new CommonResult();
		cr.setResult(CommonResult.SUCCESS);
		return cr;
	}
}

