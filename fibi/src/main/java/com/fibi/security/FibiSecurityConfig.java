package com.fibi.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import com.fibi.service.UserService;

/**
 * Spring Security configuration for FIBI
 *
 * @author pragu
 *
 */

@Configuration
@EnableOAuth2Client
public class FibiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	UserService userService;

	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Autowired
	AuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// allow signup, login and home pages
				.antMatchers("/signup.html", "/login.html", "/index.html", "/home.html", "/").permitAll()
				// allow swagger resources
				.antMatchers("/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/**").permitAll()
				// allow angular resources
				.antMatchers("/js/**", "/css/**", "/vendor/**", "/img/**").permitAll()
				// allow signup request
				.antMatchers(HttpMethod.POST, "/users").permitAll()

				// for testing
				.antMatchers(HttpMethod.POST, "/travels").permitAll()

				.antMatchers(HttpMethod.GET, "/users").permitAll().antMatchers(HttpMethod.GET, "/countries").permitAll()
				.antMatchers(HttpMethod.GET, "/travels/**").permitAll()

				.anyRequest().authenticated().and().formLogin()
				// Custom login page
				.loginPage("/login").permitAll().and()				
				.exceptionHandling()
				.authenticationEntryPoint(entryPoint())
				// disabling csrf for now. should be enabled later
				.and().csrf().disable().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}


	private Filter ssoFilter() {
		
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<Filter>();
		  
		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/facebook");
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(
				new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
		facebookFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		
		filters.add(facebookFilter);
		
		OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/google");
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
		googleFilter.setRestTemplate(googleTemplate);
		googleFilter.setTokenServices(
				new UserInfoTokenServices(googleResource().getUserInfoUri(), googleResource().getClientId()));

		googleFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		
		filters.add(googleFilter);
		
		filter.setFilters(filters);
		
		return filter;
	}

	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
	}
	
	@Bean
	@ConfigurationProperties("google.client")
	public AuthorizationCodeResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("google.resource")
	public ResourceServerProperties googleResource() {
		return new ResourceServerProperties();
	}


	@Bean
	public org.springframework.boot.context.embedded.FilterRegistrationBean oauth2ClientFilterRegistration(
			OAuth2ClientContextFilter filter) {
		org.springframework.boot.context.embedded.FilterRegistrationBean registration = new org.springframework.boot.context.embedded.FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
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
