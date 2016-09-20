package com.fibi.service;

import java.util.Date;
import java.util.List;

import com.fibi.data.Travel;

/**
 * Service to read and update{@link Travel}
 * 
 * @author pragu
 *
 * @spring.bean TravelService
 */
public interface TravelService {
	
	Travel createNewTravel(Travel travel);
	
	List<Travel> getAllTravels();

	List<Travel> getSearchTravels(String departureCity, String destinationCity,
			Date startDate, Date endDate);		
	List<Travel> getTravelDetailsById(String userId);
}
