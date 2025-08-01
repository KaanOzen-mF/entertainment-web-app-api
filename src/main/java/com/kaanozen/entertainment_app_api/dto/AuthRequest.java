package com.kaanozen.entertainment_app_api.dto;

/**
 * A Data Transfer Object (DTO) that represents the payload for authentication requests.
 * This record is used to capture the email and password submitted by a user during
 * both the registration and login processes.
 * <p>
 * Using a Java Record is a concise way to create an immutable data carrier class.
 *
 * @param email The user's email address.
 * @param password The user's plain-text password.
 */
public record AuthRequest(String email, String password) {
}
