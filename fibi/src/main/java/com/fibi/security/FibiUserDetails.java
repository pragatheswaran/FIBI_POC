package com.fibi.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class FibiUserDetails extends User {

	private static final long serialVersionUID = 1L;

	public FibiUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, authorities);
	}
	

}
