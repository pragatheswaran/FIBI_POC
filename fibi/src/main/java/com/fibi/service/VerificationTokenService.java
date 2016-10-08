package com.fibi.service;

import com.fibi.data.User;
import com.fibi.data.VerificationToken;

/**
 * Service to read {@link verificationToken}
 * 
 * @author pragu
 *
 * @spring.bean VerificationTokenService
 */
public interface VerificationTokenService {

	void createVerificationToken(User user, String token);

	VerificationToken getVerificationToken(String token);
	
}
