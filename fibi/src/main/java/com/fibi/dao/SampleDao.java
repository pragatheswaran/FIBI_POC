package com.fibi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.Sample;

/**
 * The DAO for the {@link Sample}.
 *
 * @author pragu
 *
 * @spring.bean SampleDao
 */
public interface SampleDao extends MongoRepository<Sample, String> {

}
