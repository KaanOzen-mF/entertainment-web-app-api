package com.kaanozen.entertainment_app_api.exception;

import com.kaanozen.entertainment_app_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * A global exception handler for the entire application.
 * The {@code @ControllerAdvice} annotation allows this class to intercept exceptions
 * thrown from any controller, providing a centralized place to manage error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type {@link IllegalStateException}.
     * This method is specifically triggered when, for example, a user tries to register
     * with an email that is already in use.
     *
     * @param ex The caught {@code IllegalStateException}.
     * @return A {@link ResponseEntity} containing a standardized {@link ErrorResponse}
     * and an appropriate HTTP status code (409 Conflict).
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        // 1. Create a standardized error response DTO.
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(), // 409 Conflict: Indicates a resource conflict (e.g., duplicate email).
                ex.getMessage(), // The specific error message we threw in the service (e.g., "Email already in use").
                LocalDateTime.now()
        );
        // 2. Return the DTO to the client with the 409 status code.
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
