package com.fibi.service;

import java.util.List;

import com.fibi.data.MessageBoard;

/**
 * Service to read {@link MessageBoard}
 * 
 * @author pragu
 *
 * @spring.bean MessageBoardService
 */
public interface MessageBoardService {

	List<MessageBoard> getAllMessageBoardMessages();

	MessageBoard createNewBoardMessage(MessageBoard messageBoard);

	List<MessageBoard> getBoardMessagesForCommunity(String communityCode);
	
}
