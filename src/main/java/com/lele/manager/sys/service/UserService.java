package com.lele.manager.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.sys.dao.UserDAO;
import com.lele.manager.sys.entity.User;
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
	
	public List<User> searchUser(String userName) {
		return userDao.searchUserByName(userName);
	}
	
	public User getUser(String account) {
		return userDao.getUserByName(account);
	}
}
