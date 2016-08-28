package com.fibi.exceptions;

import java.util.HashMap;

/**
 * An enum defining all error response codes we are going to use in this back
 * end application. If we want to have additional response codes, we can extend
 * this enum structure.
 *
 */
public enum ErrorCode {
    MALFORMED (400),
    UNAUTHORIZED (401),
    NOT_FOUND (404),
    NOT_ALLOWED (405),
    SERVER_ERROR (500);

	private static final HashMap<Integer, ErrorCode> typesByValue = new HashMap<Integer, ErrorCode>();
	
	static {
		for (ErrorCode type : ErrorCode.values()) {
            typesByValue.put(type.statusCode, type);
        }
    }
	 
	private final Integer statusCode;
	
	ErrorCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Get official status code for the defined enum value. 
	 * 
	 * @return
	 */
	public Integer statusCode() { return statusCode; }
	
	/**
	 * Get the ErrorCode enum based on the integer value of its statusCode.
	 * 
	 * @param value
	 * @return
	 */
	public static ErrorCode forValue(int value) {
        return typesByValue.get(value);
    }
}