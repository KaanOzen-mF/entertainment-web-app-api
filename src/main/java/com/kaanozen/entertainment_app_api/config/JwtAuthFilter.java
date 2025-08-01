package com.kaanozen.entertainment_app_api.config;

import com.kaanozen.entertainment_app_api.service.JpaUserDetailsService;
import com.kaanozen.entertainment_app_api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A custom Spring Security filter that intercepts every incoming HTTP request to validate the JWT.
 * This filter runs once per request and is responsible for authenticating the user
 * based on the token found in the 'Authorization' header.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JpaUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, JpaUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * The core logic of the filter. It checks for a JWT in the request, validates it,
     * and sets the user's authentication in the Spring Security context if the token is valid.
     *
     * @param request The incoming HTTP request.
     * @param response The HTTP response.
     * @param filterChain The chain of filters to pass the request along to.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check if the Authorization header is present and correctly formatted (starts with "Bearer ").
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If not, pass the request to the next filter in the chain and stop processing.
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract the JWT from the header by removing the "Bearer " prefix.
        jwt = authHeader.substring(7);
        // 3. Extract the user's email (which is the 'subject' of the token) from the JWT.
        userEmail = jwtService.extractUsername(jwt);

        // 4. Check if an email was extracted and if the user is not already authenticated.
        //    The second check prevents re-authenticating on every filter in the chain for a single request.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. Load the user details from the database using the email.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // 6. Validate the token against the user details (checks signature and expiration).
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 7. If the token is valid, create an authentication token.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are not needed as the user is already authenticated by the token.
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 8. Update the Spring Security context with the new authentication token.
                //    This officially marks the user as authenticated for this request.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 9. Pass the request to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}
