package com.fibi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fibi.data.PasswordResetToken;
import com.fibi.data.User;
import com.fibi.data.VerificationToken;
import com.fibi.events.OnPasswordResetEvent;
import com.fibi.events.OnRegistrationCompleteEvent;
import com.fibi.exceptions.ApiRequestException;
import com.fibi.exceptions.ErrorCode;
import com.fibi.service.PasswordResetTokenService;
import com.fibi.service.UserService;
import com.fibi.service.VerificationTokenService;
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

	@Resource
	private VerificationTokenService verificationTokenService;

	@Resource
	private PasswordResetTokenService passwordResetTokenService;

	@Resource
	private ApplicationEventPublisher applicationEventPublisher;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();

		User user = userService.getCurrentUser();
		if (user != null) {
			model.put("firstName", user.getFirstName());
			if (user.getCommunityCode() != null && user.getCommunityCode().size() > 0) {
				model.put("community", user.getCommunityCode().get(0));
			} else {
				model.put("community", "Open-Community");
			}
			if (user.getProfilePic() != null) {
				model.put("profilePic", user.getProfilePic());
			} else {
				model.put("profilePic", "img/avatarmale.jpg");
			}
		}
		return model;
	}

	@RequestMapping(value = "", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {

		List<User> userList = new ArrayList<User>();
		;
		try {
			userList = userService.getAllUsers();
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createUser(@RequestBody User user, WebRequest request) {

		User newUser = null;
		try {
			// TODO:Validations
			// TODO: Password encryption
			if (!FibiApiValidator.validate(user)) {
				throw new ApiRequestException(ErrorCode.MALFORMED, "Validation failed");
			}

			user.setEnabled(false);
			String appUrl = request.getContextPath();

			newUser = userService.createNewUser(user);

			applicationEventPublisher
					.publishEvent(new OnRegistrationCompleteEvent(newUser, appUrl, request.getLocale()));
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/confirmUser", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token,
			HttpServletResponse response) {
		Locale locale = request.getLocale();

		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		if (verificationToken == null) {
			return "false";
		}

		User user = verificationToken.getUser();
		user.setEnabled(true);
		userService.createNewUser(user);

		try {
			response.sendRedirect("/fibi");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "true";
	}

	@RequestMapping(value = "validateAndSendOTP", method = RequestMethod.POST)
	public ResponseEntity<Void> validateAndSendOTP(@RequestParam(value = "emailId", required = true) String emailId,
			WebRequest request) {

		User user = null;
		try {
			user = userService.findByEmail(emailId);

			if (user == null) {
				throw new ApiRequestException(ErrorCode.NOT_FOUND, "Invalid email address");
			} else {
				/* send OTP */
				String appUrl = request.getContextPath();
				applicationEventPublisher.publishEvent(new OnPasswordResetEvent(user, appUrl, request.getLocale()));
			}
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "resetUserPassword", method = RequestMethod.POST)
	public ResponseEntity<Void> resetUserPassword(@RequestParam(value = "otp", required = true) String otp,
			@RequestParam(value = "password", required = true) String password, WebRequest request) {

		Locale locale = request.getLocale();
		try {
			PasswordResetToken passwordResetToken = passwordResetTokenService.getPasswordResetToken(otp);
			if (passwordResetToken == null) {
				throw new ApiRequestException(ErrorCode.NOT_FOUND, "Invalid OTP");
			}

			User user = passwordResetToken.getUser();
			user.setPassword(password);
			userService.createNewUser(user);
		} catch (Exception e) {
			// Filter exceptions and set the error code accordingly
			throw new ApiRequestException(ErrorCode.SERVER_ERROR, e.getMessage());
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
