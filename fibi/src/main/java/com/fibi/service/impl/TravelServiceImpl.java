package com.fibi.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fibi.dao.TravelDao;
import com.fibi.data.Travel;
import com.fibi.service.TravelService;

/**
 * Implementation of the {@link TravelService}.
 * 
 * @author pragu
 *
 */

@Component
public class TravelServiceImpl implements TravelService {

	@Resource
	private TravelDao travelDao;

	@Resource
	private MongoOperations mongoOperations;

	@Override
	public Travel createNewTravel(Travel travel) {
		return travelDao.insert(travel);
	}

	@Override
	public List<Travel> getAllTravels() {
		return travelDao.findAll();
	}

	@Override
	public List<Travel> getSearchTravels(String departureCity, String destinationCity, Date startDate, Date endDate) {
		// List<Travel> travelList = travelDao.searchTravels(departureCity,
		// destinationCity, startDate, endDate);
		// return travelList;

		try {
			// TODO
			Query query = new Query();
			query.addCriteria(Criteria.where("departureCity").is(departureCity));
			query.addCriteria(Criteria.where("destinationCity").is(destinationCity));
			query.addCriteria(Criteria.where("startDate").gte(startDate).lt(endDate));

			query.with(new Sort(Sort.Direction.ASC, "startDate"));

			List<Travel> travelList = mongoOperations.find(query, Travel.class);
			return travelList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
            return null;
	}  
}
