package com.fibi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.SampleDao;
import com.fibi.data.Sample;
import com.fibi.service.SampleService;

/**
 * Default implementation of the {@link SampleService}.
 * 
 * @author pragu
 *
 */

@Component
public class SampleServiceImpl implements SampleService {

	@Resource
	private SampleDao sampleDao;
	 
	@Override
	public List<Sample> getAllSamples() {
		return sampleDao.findAll();
	}

}
