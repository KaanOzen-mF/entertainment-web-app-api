package com.kaanozen.entertainment_app_api;

import com.kaanozen.entertainment_app_api.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class EntertainmentAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntertainmentAppApiApplication.class, args);
	}

}
