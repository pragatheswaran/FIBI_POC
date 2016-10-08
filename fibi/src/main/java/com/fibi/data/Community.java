package com.fibi.data;

import org.springframework.data.annotation.Id;

/**
 * Data class for Commuity
 *
 * @author pragu
 *
 */
public class Community
{
	@Id
	private String id;
	
	private String code;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
