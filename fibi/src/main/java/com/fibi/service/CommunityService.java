package com.fibi.service;

import com.fibi.data.Community;

/**
 * Service to read {@link sample}
 * 
 * @author pragu
 *
 * @spring.bean CommunityService
 */
public interface CommunityService {

	Community findByCode(String code);

	Community createCommunity(Community newCommunity);

}
