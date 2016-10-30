package com.fibi.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fibi.data.User;
import com.fibi.service.UserService;
import com.fibi.util.FibiCoreUtils;

public class FibiLocalAuthProvider extends DaoAuthenticationProvider {

	private UserService userService;

	public FibiLocalAuthProvider(UserService userService) {

		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		boolean isAuthenticated = false;

		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

		User user = userService.getUserByEmail(authentication.getPrincipal().toString().toLowerCase());

		// Decrypt password
		String hashedPassword;
		try {
			hashedPassword = FibiCoreUtils.createHash(authentication.getCredentials().toString(), user.getSalt());

			if (user != null && hashedPassword.equalsIgnoreCase(user.getPassword()) && user.isEnabled()) {
				isAuthenticated = true;
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isAuthenticated) {

			FibiUserDetails userDetails = new FibiUserDetails(authentication.getName(), authentication.getName(),
					grantedAuths);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
					authentication.getCredentials(), grantedAuths);
			return auth;
		}

		return null;
	}
}
