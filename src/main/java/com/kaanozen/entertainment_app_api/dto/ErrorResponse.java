package com.kaanozen.entertainment_app_api.dto;

import java.time.LocalDateTime;

/**
 * A Data Transfer Object (DTO) that represents a standardized error response from the API.
 * This record is used by the GlobalExceptionHandler to provide consistent and informative
 * error messages to the client.
 *
 * @param statusCode The HTTP status code associated with the error (e.g., 409 for Conflict).
 * @param message A descriptive message explaining the error (e.g., "Email already in use").
 * @param timestamp The exact time the error occurred.
 */
public record ErrorResponse(
        int statusCode,
        String message,
        LocalDateTime timestamp
) {
}
