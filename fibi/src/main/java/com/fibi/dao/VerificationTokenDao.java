package com.fibi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.VerificationToken;

/**
 * The DAO for the {@link VerificationToken}.
 *
 * @author pragu
 *
 * @spring.bean VerificationTokenDao
 */
public interface VerificationTokenDao extends MongoRepository<VerificationToken, String> {

	VerificationToken findByToken(String token);

}
