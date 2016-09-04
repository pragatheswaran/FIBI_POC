package com.fibi.data;

import org.springframework.data.annotation.Id;

/**
 * Data class for Country
 *
 * @author pragu
 *
 */
public class Country
{
	@Id
	private String id;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
