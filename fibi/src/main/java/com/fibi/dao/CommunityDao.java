package com.fibi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.Community;
import com.fibi.data.Sample;

/**
 * The DAO for the {@link Sample}.
 *
 * @author pragu
 *
 * @spring.bean CommunityDao
 */
public interface CommunityDao extends MongoRepository<Community, String> {

	Community findByCode(String code);

}
