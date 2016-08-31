package com.fibi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.UserDao;
import com.fibi.data.User;
import com.fibi.service.UserService;

/**
 * Implementation of the {@link UserService}.
 * 
 * @author pragu
 *
 */

@Component
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User createNewUser(User user) {
		return userDao.insert(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	 

}
