package com.fibi.service;

import com.fibi.data.PasswordResetToken;
import com.fibi.data.User;

/**
 * Service to read {@link passwordResetToken}
 * 
 * @author pragu
 *
 * @spring.bean PasswordResetTokenService
 */
public interface PasswordResetTokenService {

	void createPasswordResetToken(User user, String otp);

	PasswordResetToken getPasswordResetToken(String otp);
	
}
