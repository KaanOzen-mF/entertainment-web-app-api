// src/main/java/com/example/entertainmentappapi/controller/AuthController.java
package com.kaanozen.entertainment_app_api.controller;

import com.kaanozen.entertainment_app_api.dto.AuthRequest;
import com.kaanozen.entertainment_app_api.dto.AuthResponse;
import com.kaanozen.entertainment_app_api.entity.User;
import com.kaanozen.entertainment_app_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling user authentication endpoints, such as registration and login.
 * All routes in this controller are prefixed with "/api/v1/auth".
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000") // Note: This is redundant if global CORS is configured in SecurityConfig, but doesn't hurt.
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles the user registration request.
     *
     * @param request An AuthRequest object containing the user's email and password.
     * @return A ResponseEntity containing the newly created User object and an HTTP 201 CREATED status.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequest request) {
        User registeredUser = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    /**
     * Handles the user login request.
     *
     * @param request An AuthRequest object containing the user's email and password.
     * @return A ResponseEntity containing an AuthResponse with the generated JWT and an HTTP 200 OK status.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
