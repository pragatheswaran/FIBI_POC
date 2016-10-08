package com.fibi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fibi.dao.MessageBoardDao;
import com.fibi.data.MessageBoard;
import com.fibi.service.MessageBoardService;

/**
 * Default implementation of the {@link MessageBoardService}.
 * 
 * @author pragu
 *
 */

@Component
public class MessageBoardServiceImpl implements MessageBoardService {

	@Resource
	private MessageBoardDao messageBoardDao;

	@Override
	public List<MessageBoard> getAllMessageBoardMessages() {
		// TODO Auto-generated method stub
		return messageBoardDao.findAll();
	}

	@Override
	public MessageBoard createNewBoardMessage(MessageBoard messageBoard) {
		// TODO Auto-generated method stub
		return messageBoardDao.save(messageBoard);
	}

	@Override
	public List<MessageBoard> getBoardMessagesForCommunity(String communityCode) {
		// TODO Auto-generated method stub
		return messageBoardDao.findByCommunityCodeOrderByPostedDateAsc(communityCode);
	}
	 

}
