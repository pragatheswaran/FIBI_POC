package com.fibi.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
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

//@EnableOAuth2Sso
//@EnableWebSecurity
public class FibiSecurityConfigBkp extends WebSecurityConfigurerAdapter {
	
	@Resource
	UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {       
		
		http
		  .authorizeRequests()
		   //allow signup, login and home pages
		   .antMatchers("/signup.html", "/login.html", "/index.html", "/home.html", "/").permitAll()
		   //allow swagger resources
		   .antMatchers("/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/**").permitAll()
		   //allow angular resources
		   .antMatchers("/js/**", "/css/**", "/vendor/**", "/img/**").permitAll()
		   //allow signup request
		   .antMatchers(HttpMethod.POST,"/users").permitAll()
		   
		   //for testing
		   .antMatchers(HttpMethod.POST,"/travels").permitAll()
		   
		   .antMatchers(HttpMethod.GET,"/users").permitAll()
		   .antMatchers(HttpMethod.GET,"/countries").permitAll()
		   .antMatchers(HttpMethod.GET,"/travels/**").permitAll()
		   
		   .anyRequest().authenticated()
		   .and()
		   .formLogin()
		   //Custom login page
		   .loginPage("/login").permitAll()
		   .and()
		   .exceptionHandling().authenticationEntryPoint(entryPoint())
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
