package com.kaanozen.entertainment_app_api.config;

import com.kaanozen.entertainment_app_api.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Main security configuration class for the application.
 * This class enables web security and defines the rules for authentication,
 * authorization, CORS, and session management.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Defines the main security filter chain that applies to all HTTP requests.
     * This is where we configure which endpoints are public and which are protected.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS using the custom configuration defined in the corsConfigurationSource bean.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF (Cross-Site Request Forgery) protection, as we are using stateless JWT authentication.
                .csrf(csrf -> csrf.disable())
                // Define authorization rules for HTTP requests.
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access to all endpoints under "/api/v1/auth/" (e.g., login, register).
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Require authentication for all other requests.
                        .anyRequest().authenticated()
                )
                // Configure session management to be STATELESS. The server will not create or use HTTP sessions.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set the custom authentication provider.
                .authenticationProvider(authenticationProvider())
                // Add our custom JWT filter to be executed before the standard username/password authentication filter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures the global CORS (Cross-Origin Resource Sharing) policy for the application.
     *
     * @return A CorsConfigurationSource with the defined rules.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests specifically from our frontend application's origin.
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Specify the allowed HTTP methods.
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        // Specify the allowed HTTP headers.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this CORS configuration to all routes in the application.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Creates the AuthenticationProvider bean.
     * This provider links our custom UserDetailsService (for finding users)
     * with our PasswordEncoder (for verifying passwords).
     *
     * @return The configured DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Set the service that Spring Security will use to load user details.
        authProvider.setUserDetailsService(jpaUserDetailsService);
        // Set the password encoder that Spring Security will use to hash and verify passwords.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates the PasswordEncoder bean, which uses the strong BCrypt hashing algorithm.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the AuthenticationManager as a bean, which is required for the
     * authentication process in our AuthService.
     *
     * @param authenticationConfiguration The authentication configuration.
     * @return The AuthenticationManager.
     * @throws Exception If an error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
