package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fibi.data.Country;
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
}
