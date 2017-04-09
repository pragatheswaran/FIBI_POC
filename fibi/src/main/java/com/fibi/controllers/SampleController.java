package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fibi.data.Sample;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.SampleService;

/**
 * Sample REST controller 
 *
 * @author pragu
 *
 */
@RestController
@RequestMapping(value = "/sample")
public class SampleController {
	
	private final static Logger LOGGER = Logger.getLogger(SampleController.class.getName());
	
	@Resource
	private SampleService sampleService;
	
		
	@RequestMapping(value = "", produces = { APPLICATION_JSON_VALUE }, method = RequestMethod.GET)	
	public ResponseEntity<List<Sample>> getSamples() {
		
		List<Sample> sampleList = new ArrayList<Sample>();;
	
		try {
			
			//Perform validation using validator for POST|PUT|DELETE
			//Add Logs
			
			sampleList = sampleService.getAllSamples();
		} catch(Exception e){
			
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<List<Sample>>(sampleList, HttpStatus.OK);
	}

}
