package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A service dedicated to handling JSON Web Token (JWT) operations.
 * This includes generating new tokens, extracting information from them,
 * and validating their integrity and expiration.
 */
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Extracts the username (email) from a given JWT.
     *
     * @param token The JWT string.
     * @return The username (email) contained within the token's subject claim.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Validates a JWT against a user's details.
     *
     * @param token       The JWT string to validate.
     * @param userDetails The UserDetails object representing the user.
     * @return {@code true} if the token is valid for the user and not expired, otherwise {@code false}.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    /**
     * Generates a new JWT for a given user.
     *
     * @param userDetails The UserDetails object for whom the token will be generated.
     * @return The compacted JWT string.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    /**
     * Generates a new JWT with extra claims for a given user.
     *
     * @param extraClaims Additional claims to include in the JWT payload.
     * @param userDetails The UserDetails object for whom the token will be generated.
     * @return The compacted JWT string.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 saat geçerlilik süresi
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * Checks if a token has expired.
     *
     * @param token The JWT string.
     * @return {@code true} if the token's expiration date is before the current date, otherwise {@code false}.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /**
     * Extracts the expiration date from a token.
     *
     * @param token The JWT string.
     * @return The expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * A generic function to extract a specific claim from a token.
     *
     * @param token          The JWT string.
     * @param claimsResolver A function that specifies how to extract the desired claim.
     * @param <T>            The type of the claim.
     * @return The extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
     * Extracts all claims from a token by parsing it with the signing key.
     *
     * @param token The JWT string.
     * @return The Claims object containing all data from the token's payload.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    /**
     * Decodes the Base64 secret key from the configuration and prepares it for signing.
     *
     * @return The signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.key());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
