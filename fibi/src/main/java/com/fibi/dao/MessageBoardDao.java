package com.fibi.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fibi.data.MessageBoard;

/**
 * The DAO for the {@link MessageBoard}.
 *
 * @author pragu
 *
 * @spring.bean MessageBoardDao
 */
public interface MessageBoardDao extends MongoRepository<MessageBoard, String> {

	List<MessageBoard> findByCommunityCodeOrderByPostedDateAsc(String communityCode);

}
