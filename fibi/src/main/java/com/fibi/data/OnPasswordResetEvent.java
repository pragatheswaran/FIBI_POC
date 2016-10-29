package com.fibi.data;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

public class OnPasswordResetEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2609865993718810758L;

	private final User user;
	private final String appUrl;
	private final Locale locale;

	public OnPasswordResetEvent(User user, String appUrl, Locale locale) {
		super(user);

		this.user = user;
		this.appUrl = appUrl;
		this.locale = locale;
	}

	public User getUser() {
		return user;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}
}