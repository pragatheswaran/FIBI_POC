package com.fibi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.CountryDao;
import com.fibi.data.Country;
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

	@Override
	public List<Country> getAllCountries() {
		return countryDao.findAll();
	}
}
