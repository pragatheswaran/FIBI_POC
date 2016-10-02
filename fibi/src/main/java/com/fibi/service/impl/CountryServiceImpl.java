package com.fibi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fibi.dao.CountryDao;
import com.fibi.data.Country;
import com.fibi.data.Travel;
import com.fibi.service.CountryService;

/**
 * Implementation of the {@link CountryService}.
 * 
 * @author pragu
 *
 */

@Component
public class CountryServiceImpl implements CountryService {

	@Resource
	private CountryDao countryDao;
	

	@Resource
	private MongoOperations mongoOperations;

	@Override
	public List<Country> getAllCountries() {
		return countryDao.findAll();
	}

	@Override
	public void dumpCountries(List<Country> countries) {
		countryDao.insert(countries);
	}

	@Override
	public Country getCountryByName(String country) {
		return countryDao.findByName(country);
	}

	@Override
	public List<Country> getAllCountryNames() {
		
		List<Country> countriesNameList = null;

		Query query = new Query();
		query.fields().include("name");

		countriesNameList = mongoOperations.find(query, Country.class);

		return countriesNameList;

	}
}
