// src/main/java/com/example/entertainmentappapi/service/AuthService.java
package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.dto.AuthRequest;
import com.kaanozen.entertainment_app_api.entity.User;
import com.kaanozen.entertainment_app_api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that handles the core business logic for user authentication,
 * including registration and login.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs the AuthService with required dependencies injected by Spring.
     *
     * @param userRepository      Repository for user data access.
     * @param passwordEncoder     Service for hashing and verifying passwords.
     * @param jwtService          Service for generating and validating JWTs.
     * @param authenticationManager Spring Security's manager for handling the authentication process.
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user in the system.
     *
     * @param request A DTO containing the new user's email and password.
     * @return The saved User entity.
     * @throws IllegalStateException if the email is already in use.
     */
    public User register(AuthRequest request) {
        // 1. Check if a user with the given email already exists to prevent duplicates.
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        // 2. Create a new User entity.
        User newUser = new User();
        newUser.setEmail(request.email());
        // 3. IMPORTANT: Always encode the password before saving it to the database.
        newUser.setPassword(passwordEncoder.encode(request.password()));

        // 4. Save the new user to the database and return the persisted entity.
        return userRepository.save(newUser);
    }

    /**
     * Authenticates a user and generates a JWT upon successful login.
     *
     * @param request A DTO containing the user's login credentials (email and password).
     * @return A JWT string for the authenticated user.
     */
    public String login(AuthRequest request) {
        // 1. Use Spring Security's AuthenticationManager to validate the credentials.
        //    This will internally use our JpaUserDetailsService to find the user
        //    and our PasswordEncoder to compare the passwords.
        //    An exception is automatically thrown if authentication fails.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // 2. If authentication is successful, retrieve the user details from the database.
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found after successful authentication"));

        // 3. Generate and return a JWT for the authenticated user.
        return jwtService.generateToken(user);
    }
}
