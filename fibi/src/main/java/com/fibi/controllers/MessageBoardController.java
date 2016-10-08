package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fibi.data.MessageBoard;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.MessageBoardService;

/**
 * MessageBoard REST controller 
 *
 * @author pragu
 *
 */
@RestController
@RequestMapping(value = "/messageBoard")
public class MessageBoardController {
	
	private final static Logger LOGGER = Logger.getLogger(MessageBoardController.class.getName());
	
	@Resource
	private MessageBoardService messageBoardService;
	
		
	@RequestMapping(value = "", produces = { APPLICATION_JSON_VALUE }, method = RequestMethod.GET)	
	public ResponseEntity<List<MessageBoard>> getAllMessageBoardMessages() {
		
		List<MessageBoard> messageBoardList = new ArrayList<MessageBoard>();;
	
		try {
			messageBoardList = messageBoardService.getAllMessageBoardMessages();
		} catch(Exception e){		
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<List<MessageBoard>>(messageBoardList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)	
	public ResponseEntity<Void> createBoardMessageForCommunity(@RequestBody MessageBoard messageBoard) {
		
		MessageBoard newMessage = null;		
		try {
			//TODO:Validations			
			newMessage = messageBoardService.createNewBoardMessage(messageBoard);
		} catch(Exception e){
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/community/{code}", produces = { APPLICATION_JSON_VALUE }, method = RequestMethod.GET)	
	public ResponseEntity<List<MessageBoard>> getBoardMessagesForCommunity(@PathVariable String code) {
		
		List<MessageBoard> messageBoardList = new ArrayList<MessageBoard>();;
	
		try {
			messageBoardList = messageBoardService.getBoardMessagesForCommunity(code);
		} catch(Exception e){		
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<List<MessageBoard>>(messageBoardList, HttpStatus.OK);
	}



}
