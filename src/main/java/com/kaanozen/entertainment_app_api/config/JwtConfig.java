package com.kaanozen.entertainment_app_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A configuration properties class designed to map properties from `application.properties`
 * into a type-safe Java object. This class specifically handles the JWT secret key.
 * <p>
 * Using {@code @ConfigurationProperties} is the modern, recommended way in Spring Boot
 * to handle configuration, as it provides compile-time safety and avoids scattering
 * {@code @Value} annotations throughout the codebase.
 *
 * @param key The JWT secret key, which will be automatically populated from the
 * 'jwt.secret.key' property in the application.properties file.
 */
@ConfigurationProperties(prefix = "jwt.secret")
public record JwtConfig(String key) {
}
