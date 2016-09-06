package com.fibi.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.fibi.data.Travel;

/**
 * The DAO for the {@link Travel}.
 *
 * @author pragu
 *
 * @spring.bean TravelDao
 */
public interface TravelDao extends MongoRepository<Travel, String> {

	@Query(value="{'departureCity' : ?0},{'destinationCity' : ?1},{'startDate' : {'$gt' : ?2, '$lt' : ?3}}")
	List<Travel> searchTravels(String departureCity, String destinationCity, Date startDate, Date endDate);
}
