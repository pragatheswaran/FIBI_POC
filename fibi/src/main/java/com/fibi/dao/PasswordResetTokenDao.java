package com.fibi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.PasswordResetToken;

/**
 * The DAO for the {@link PasswordResetToken}.
 *
 * @author pragu
 *
 * @spring.bean PasswordResetTokenDao
 */
public interface PasswordResetTokenDao extends MongoRepository<PasswordResetToken, String> {

	PasswordResetToken findByOtp(String otp);

}
