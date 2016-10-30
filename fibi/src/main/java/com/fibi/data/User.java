package com.fibi.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

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
	
	private String email;
	private String firstName;
	private String lastName;
	private String country;
	private String password;
	private byte[] salt;
	private String profilePic;	
	private String city;
	private List<String> communityCode = new ArrayList<String>();
	//private Community community;
	private boolean enabled;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	/*public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}*/
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<String> getCommunityCode() {
		return communityCode;
	}
	public void setCommunityCode(List<String> communityCode) {
		this.communityCode = communityCode;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
