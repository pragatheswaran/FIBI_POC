package com.fibi.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);

	}

	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth instanceof UsernamePasswordAuthenticationToken) {
			return getUserByEmail(auth.getName());
		} 
		else if(auth instanceof OAuth2Authentication) {
			User oauthUser = new User();
			//auth.
			UsernamePasswordAuthenticationToken userNameAuth = (UsernamePasswordAuthenticationToken) ((OAuth2Authentication) auth).getUserAuthentication();
			LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>) userNameAuth.getDetails();
			oauthUser.setEmail(userDetails.get("email"));
			if(userDetails.get("given_name")!=null && userDetails.get("given_name").length() > 0) {
				oauthUser.setFirstName(userDetails.get("given_name"));	
			}else {
				oauthUser.setFirstName(userDetails.get("first_name"));	
			}
			
			if ((Object) userDetails.get("picture") instanceof LinkedHashMap) {
				Object pic = (Object) userDetails.get("picture");
				LinkedHashMap<String, String> picMap = (LinkedHashMap<String, String>) pic;
				Object picData = (Object) picMap.get("data");
				LinkedHashMap<String, String> picDataMap = (LinkedHashMap<String, String>) picData;
				oauthUser.setProfilePic(picDataMap.get("url"));
			} else {
				oauthUser.setProfilePic(userDetails.get("picture"));
			}				
						
			return oauthUser;					
		}
		
		return null;
	}	 

}
