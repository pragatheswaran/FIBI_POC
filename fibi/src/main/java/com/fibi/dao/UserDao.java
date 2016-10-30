package com.fibi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.User;

/**
 * The DAO for the {@link User}.
 *
 * @author pragu
 *
 * @spring.bean UserDao
 */
public interface UserDao extends MongoRepository<User, String> {

	User findByEmail(String email);
}
