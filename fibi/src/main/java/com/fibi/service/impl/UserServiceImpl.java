package com.fibi.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import com.fibi.dao.UserDao;
import com.fibi.data.Community;
import com.fibi.data.User;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.CommunityService;
import com.fibi.service.UserService;
import com.fibi.util.FibiCoreUtils;

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

	@Resource
	private CommunityService communityService;

	@Override
	public User createNewUser(User user) {

		String email = user.getEmail();

		if (user.getId() == null) {
			User oldUser = getUserByEmail(email);
			if (oldUser != null) {
				throw new ApiRequestException(ErrorCode.USER_ALREADY_EXISTS, email + " already exists");
			}

			// Encrypt password
			byte[] genSalt;
			try {
				genSalt = FibiCoreUtils.generateSalt();
				String hashedPassword = FibiCoreUtils.createHash(user.getPassword(), genSalt);
				user.setPassword(hashedPassword);
				user.setSalt(genSalt);
			} catch (NoSuchAlgorithmException e) {
				throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
			} catch (InvalidKeySpecException e) {
				throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
			}
		}

		/* Create community if not exists */
		Community community = communityService.findByCode(user.getCity() + "(" + user.getCountry() + ")");
		List<String> userCommunities = user.getCommunityCode();

		if (community == null) {

			Community newCommunity = new Community();
			newCommunity.setName(user.getCity());
			newCommunity.setCode(user.getCity() + "(" + user.getCountry() + ")");

			newCommunity = communityService.createCommunity(newCommunity);

			userCommunities.add(newCommunity.getCode());

			user.setCommunityCode(userCommunities);
			// user.setCommunity(newCommunity.);
		} else {
			// user.setCommunity(community);
			if (user.getId() != null) {
				// TODO:
			} else {
				userCommunities.add(community.getCode());
			}
		}

		user.setEmail(email.toLowerCase());

		return userDao.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email.toLowerCase());
	}

	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth instanceof UsernamePasswordAuthenticationToken) {
			return getUserByEmail(auth.getName());
		} else if (auth instanceof OAuth2Authentication) {
			User oauthUser = new User();
			// auth.
			UsernamePasswordAuthenticationToken userNameAuth = (UsernamePasswordAuthenticationToken) ((OAuth2Authentication) auth)
					.getUserAuthentication();
			LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>) userNameAuth.getDetails();
			oauthUser.setEmail(userDetails.get("email"));
			if (userDetails.get("given_name") != null && userDetails.get("given_name").length() > 0) {
				oauthUser.setFirstName(userDetails.get("given_name"));
			} else {
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
