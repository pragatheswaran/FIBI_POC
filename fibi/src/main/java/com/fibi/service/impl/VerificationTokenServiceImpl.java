package com.fibi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.VerificationTokenDao;
import com.fibi.data.User;
import com.fibi.data.VerificationToken;
import com.fibi.service.VerificationTokenService;

/**
 * Default implementation of the {@link VerificationTokenService}.
 * 
 * @author pragu
 *
 */

@Component
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Resource
	private VerificationTokenDao verificationTokenDao;

	@Override
	public void createVerificationToken(User user, String token) {
		
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationToken.setVerified(false);
		//verificationToken.setExpiryDate(expiryDate);
		
		verificationTokenDao.save(verificationToken);
		
	}

	@Override
	public VerificationToken getVerificationToken(String token) {
		return verificationTokenDao.findByToken(token);
	}
	 

}
