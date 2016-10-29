package com.fibi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.PasswordResetTokenDao;
import com.fibi.data.PasswordResetToken;
import com.fibi.data.User;
import com.fibi.service.PasswordResetTokenService;

/**
 * Default implementation of the {@link PasswordResetTokenService}.
 *
 * @author pragu
 *
 */

@Component
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	@Resource
	private PasswordResetTokenDao passwordResetTokenDao;

	@Override
	public void createPasswordResetToken(User user, String otp) {
		// TODO Auto-generated method stub
	    PasswordResetToken passwordResetToken = new PasswordResetToken();
	    passwordResetToken.setOtp(otp);
	    passwordResetToken.setUser(user);
		//passwordResetToken.setExpiryDate(expiryDate);
		
	    passwordResetTokenDao.save(passwordResetToken);

		
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String otp) {
		return passwordResetTokenDao.findByOtp(otp);
	}
	 

}
