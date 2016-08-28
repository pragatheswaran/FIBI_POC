package com.fibi.exceptions;

/**
 * The ApiRequestException is thrown in case something goes wrong in the FIBI
 * back end application.
 * 
 * Instead of throwing generic exceptions every exception that is not of type
 * ApiRequestException will be converted to one and then be rethrown.
 * 
 */
public class ApiRequestException extends RuntimeException {

	private static final long serialVersionUID = -5774534553461449257L;

	private ErrorCode statusCode;
	private String message;

	public ApiRequestException(ErrorCode statusCode, String message) {
		super(message);

		this.statusCode = statusCode;
		this.message = message;
	}

	public ApiRequestException(ErrorCode statusCode, String message, Throwable cause) {
		super(message, cause);

		this.statusCode = statusCode;
		this.message = message;
	}

	public ErrorCode getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(ErrorCode statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
