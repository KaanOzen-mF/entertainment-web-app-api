package com.kaanozen.entertainment_app_api.dto;

/**
 * A Data Transfer Object (DTO) that represents the successful authentication response.
 * This record is used to send the generated JSON Web Token (JWT) back to the client
 * after a successful login.
 *
 * @param token The generated JWT string that the client will use for authenticating subsequent requests.
 */
public record AuthResponse(String token) {
}
