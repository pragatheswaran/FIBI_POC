package com.fibi.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fibi.service.UserService;

/**
 * Spring Security configuration for FIBI 
 *
 * @author pragu
 *
 */

@EnableWebSecurity
public class FibiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Resource
	UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {       
		
		http
		  .authorizeRequests()
		   //allow signup, login and home pages
		   .antMatchers("/signup.html", "/login.html", "/home.html").permitAll()
		   //allow swagger resources
		   .antMatchers("/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/**").permitAll()
		   //allow signup request
		   .antMatchers(HttpMethod.POST,"/users").permitAll()
		   //for testing
		   .antMatchers(HttpMethod.GET,"/users").permitAll()
		   .anyRequest().authenticated()
		   .and()
		   .formLogin()
		   //Custom login page
		   //.loginPage("/login").permitAll();
		   //.and()
		   //.exceptionHandling().authenticationEntryPoint(entryPoint()); 
		   
		   //disabling csrf for now. should be enabled later
		   .and().csrf().disable();
		  
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
         authManagerBuilder.parentAuthenticationManager(authenticationManager());
    }

    protected AuthenticationManager authenticationManager() {
         return new ProviderManager(Arrays.asList(localAuthenticationProvider()));
    }

    public AuthenticationProvider localAuthenticationProvider() {
  	  FibiLocalAuthProvider localAuthProvider = new FibiLocalAuthProvider(userService);
        return localAuthProvider;
    }

    private AuthenticationEntryPoint entryPoint() {
         return new LoginUrlAuthenticationEntryPoint("/login.html");
    }	
}
