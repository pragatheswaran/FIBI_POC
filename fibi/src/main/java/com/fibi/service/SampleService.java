package com.fibi.service;

import java.util.List;

import com.fibi.data.Sample;

/**
 * Service to read {@link sample}
 * 
 * @author pragu
 *
 * @spring.bean SampleService
 */
public interface SampleService {
	List<Sample> getAllSamples();
}
