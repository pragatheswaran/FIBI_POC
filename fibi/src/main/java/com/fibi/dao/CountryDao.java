package com.fibi.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.Country;

/**
 * The DAO for the {@link Country}.
 *
 * @author pragu
 *
 * @spring.bean UserDao
 */
public interface CountryDao extends MongoRepository<Country, String> {

	Country findByName(String country);

}
