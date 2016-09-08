package com.fibi;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point: Boot starts the application
 *
 * @author pragu
 *
 */
@SpringBootApplication
public class FibiApp {
	public static void main(final String[] args) {

		SpringApplication.run(FibiApp.class, args);

		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
