package com.fibi.data;

import org.springframework.data.annotation.Id;

import com.sun.istack.internal.NotNull;

/**
 * Data class for User
 *
 * @author pragu
 *
 */
public class User
{
	@Id
	private String id;
	
	@NotNull
	private String email;
	private String firstName;
	private String lastName;
	private String country;
	private String password;
	
	//TODO:??
	//private String city;
    //private String pin;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
