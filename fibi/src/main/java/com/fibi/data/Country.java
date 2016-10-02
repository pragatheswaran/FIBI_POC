package com.fibi.data;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> cities = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}	
}
