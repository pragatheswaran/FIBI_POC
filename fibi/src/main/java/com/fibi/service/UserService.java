package com.fibi.service;

import java.util.List;

import com.fibi.data.User;

/**
 * Service to read and update{@link user}
 * 
 * @author pragu
 *
 * @spring.bean UserService
 */
public interface UserService {
	
	User createNewUser(User user);
	
	List<User> getAllUsers();
	
	User getUserByEmail(String email);

	User findByEmail(String email);
	
}
