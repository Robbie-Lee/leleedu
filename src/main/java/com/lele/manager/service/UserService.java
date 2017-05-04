package com.lele.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lele.manager.dao.UserDAO;
import com.lele.manager.entity.User;
import com.lele.manager.utils.AES;

@Service("userService")
public class UserService {

	@Autowired
	UserDAO userDao;
	
	public List<User> getUserList() {
		return userDao.findAll();
	}
	
	public void activeUser(long userId, boolean active) {
		userDao.activeUser(userId, active);
	}
	
	public void addUser(User user) {
		userDao.save(user);
	}
	
	public void changePassword(long userId, String inputPassword) {
		userDao.changePassword(userId, AES.AESEncrypt(inputPassword));
	}
	
	public User getUser(String account) {
		return userDao.getUserByName(account);
	}
}
