package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fibi.data.User;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.UserService;
import com.fibi.validator.FibiApiValidator;

/**
 * User REST controller 
 *
 * @author pragu
 *
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("content", SecurityContextHolder.getContext().getAuthentication().getName());
		return model;
	}

		
	@RequestMapping(value = "", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)	
	public ResponseEntity<List<User>> getUsers() {
		
		List<User> userList = new ArrayList<User>();;		
		try {			
			userList = userService.getAllUsers();
		} catch(Exception e){
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)	
	public ResponseEntity<Void> createUser(@RequestBody User user) {
		
		User newUser = null;		
		try {
			//TODO:Validations
			//TODO: Password encryption
			if(!FibiApiValidator.validate(user)) {
			  throw new ApiRequestException(ErrorCode.MALFORMED, "Validation failed");
			}
			
			newUser = userService.createNewUser(user);
		} catch(Exception e){
			//Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
