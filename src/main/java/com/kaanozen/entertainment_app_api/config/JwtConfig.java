package com.kaanozen.entertainment_app_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.secret")
public record JwtConfig(String key) {
}
