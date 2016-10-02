package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fibi.data.Country;
import com.fibi.data.User;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.CountryService;

/**
 * User REST controller 
 *
 * @author pragu
 *
 */
@RestController
@RequestMapping(value = "/countries")
public class CountryController {
	
	@Resource
	private CountryService countryService;
	
	@RequestMapping(value = "", produces = APPLICATION_JSON_VALUE, method = RequestMethod.POST)	
	public ResponseEntity<Void> dumpCountries(@RequestBody List<Country> countries) {
		
		try {
			countryService.dumpCountries(countries);
		} catch (Exception e) {
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}	

			
	@RequestMapping(value = "", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)	
	public ResponseEntity<List<Country>> getCountries() {
		
		List<Country> countryList = new ArrayList<Country>();;		
		try {			
			countryList = countryService.getAllCountries();
		} catch(Exception e){
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<List<Country>>(countryList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/names", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)	
	public ResponseEntity<List<Country>> getCountryNames() {
		
		List<Country> countryList = new ArrayList<Country>();;		
		try {			
			countryList = countryService.getAllCountryNames();
		} catch(Exception e){
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<List<Country>>(countryList, HttpStatus.OK);
	}

	
	@RequestMapping(value = "{country}", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)	
	public ResponseEntity<Country> getCountry(@PathVariable String country) {
		
		Country countryObj = null;
		try {
			countryObj = countryService.getCountryByName(country);
		} catch (Exception e) {
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<Country>(countryObj, HttpStatus.OK);
	}	

}
