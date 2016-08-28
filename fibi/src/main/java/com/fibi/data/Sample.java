package com.fibi.data;

import org.springframework.data.annotation.Id;

/**
 * Data class for Sample
 *
 * @author pragu
 *
 */
public class Sample
{
	@Id
	private String id;
	
	private String name;
	private String email;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
