package com.fibi.service;

import java.util.List;

import com.fibi.data.Country;

/**
 * Service to read and update{@link Country}
 * 
 * @author pragu
 *
 * @spring.bean CountryService
 */
public interface CountryService {
	
	List<Country> getAllCountries();

	void dumpCountries(List<Country> countries);

	Country getCountryByName(String country);

	List<Country> getAllCountryNames();		
}
