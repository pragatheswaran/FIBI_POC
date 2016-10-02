package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fibi.data.Travel;
import com.fibi.data.User;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.TravelService;
import com.fibi.service.UserService;
import com.fibi.validator.FibiApiValidator;

/**
 * Travel REST controller
 *
 * @author pragu
 *
 */
@RestController
@RequestMapping(value = "/travels")
public class TravelController {

	@Resource
	private TravelService travelService;
	
	@Resource
	private UserService userService;

	@RequestMapping(value = "", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Travel>> getTravels() {

		List<Travel> travelList = new ArrayList<Travel>();
		;
		try {
			travelList = travelService.getAllTravels();
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<List<Travel>>(travelList, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createTravel(@RequestBody Travel travel) {

		Travel newTravel = null;
		try {
			// TODO:Validations
			if (!FibiApiValidator.validate(travel)) {
				throw new ApiRequestException(ErrorCode.MALFORMED, "Validation failed");
			}
			
			User user = userService.getCurrentUser();

			travel.setUser(user);
			
			newTravel = travelService.createNewTravel(travel);
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "search", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Travel>> searchTravels(
			@RequestParam(value = "departureCity", required = true) String departureCity,
			@RequestParam(value = "destinationCity", required = true) String destinationCity,
			@RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			@RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

		List<Travel> travelList = new ArrayList<Travel>();
		;
		try {
			travelList = travelService.getSearchTravels(departureCity, destinationCity, startDate, endDate);
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<List<Travel>>(travelList, HttpStatus.OK);
	}

}
