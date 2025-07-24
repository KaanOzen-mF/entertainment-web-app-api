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

@Component // Bu sınıfın bir Spring Bean'i olduğunu belirtiyoruz.
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JpaUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, JpaUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Authorization header'ı var mı ve "Bearer " ile başlıyor mu diye kontrol et.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Eğer yoksa, zincirdeki bir sonraki filtreye devam et.
            return;
        }

        // 2. "Bearer " kısmını atarak sadece token'ı al.
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // Token'dan kullanıcı email'ini çıkar.

        // 3. Email var mı ve kullanıcı daha önce authenticate olmamış mı diye kontrol et.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // 4. Token geçerli mi diye kontrol et.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Eğer geçerliyse, kullanıcıyı authenticate et.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // SecurityContextHolder'ı güncelle.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 5. Zincirdeki bir sonraki filtreye devam et.
        filterChain.doFilter(request, response);
    }
}