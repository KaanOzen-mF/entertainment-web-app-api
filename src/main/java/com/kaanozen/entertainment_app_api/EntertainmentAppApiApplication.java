package com.kaanozen.entertainment_app_api;

import com.kaanozen.entertainment_app_api.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * The main entry point for the Entertainment App Spring Boot application.
 */
@SpringBootApplication
// This annotation enables support for @ConfigurationProperties beans.
// It tells Spring to scan for classes annotated with @ConfigurationProperties (like our JwtConfig)
// and register them as beans in the application context.
@EnableConfigurationProperties(JwtConfig.class)
public class EntertainmentAppApiApplication {

	/**
	 * The main method which serves as the entry point for the Java application.
	 * It delegates to Spring Boot's SpringApplication class to launch the application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(EntertainmentAppApiApplication.class, args);
	}

}
