package com.fibi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.CommunityDao;
import com.fibi.data.Community;
import com.fibi.service.CommunityService;

/**
 * Default implementation of the {@link CommunityService}.
 * 
 * @author pragu
 *
 */

@Component
public class CommunityServiceImpl implements CommunityService {

	@Resource
	private CommunityDao communityDao;

	@Override
	public Community findByCode(String code) {
		return communityDao.findByCode(code);
	}

	@Override
	public Community createCommunity(Community newCommunity) {
	    return communityDao.save(newCommunity);
	}
	 

}
