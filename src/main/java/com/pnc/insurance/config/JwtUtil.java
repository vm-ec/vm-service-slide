package com.pnc.insurance.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT token generation and validation.
 */
@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username (subject) from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimsResolver function to extract the desired claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Validates JWT format before attempting to parse it.
     * Ensures token has 3 parts separated by dots and each part is valid Base64.
     *
     * @param token the JWT token to validate
     * @return true if token format is valid, false otherwise
     */
    private boolean isValidJwtFormat(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        // Check basic JWT pattern (3 parts separated by dots)
        if (!token.matches(JwtConstants.JWT_PATTERN)) {
            return false;
        }

        // Try to validate that each part is valid Base64
        String[] parts = token.split("\\.");
        for (String part : parts) {
            try {
                Base64.getUrlDecoder().decode(part);
            } catch (IllegalArgumentException e) {
                log.debug("Invalid Base64 in JWT part: {}", e.getMessage());
                return false;
            }
        }

        return true;
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the Claims object containing all claims from the token
     * @throws IllegalArgumentException if token is invalid
     * @throws io.jsonwebtoken.JwtException if JWT parsing fails
     */
    private Claims extractAllClaims(String token) {
        try {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("Token is empty or null");
            }

            // Remove all whitespace including newlines, tabs, etc.
            String cleanToken = token.replaceAll("\\s+", "");

            if (cleanToken.isBlank()) {
                throw new IllegalArgumentException("Token contains only whitespace");
            }

            // Pre-validate JWT format
            if (!isValidJwtFormat(cleanToken)) {
                throw new IllegalArgumentException("Token does not match JWT format");
            }

            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(cleanToken).getBody();
        } catch (io.jsonwebtoken.JwtException e) {
            log.debug("JWT parsing error: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.debug("JWT validation error: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token
     * @return true if token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            log.debug("Token expiration check failed: {}", e.getMessage());
            return true;
        }
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username to encode in the token
     * @return the generated JWT token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Creates a JWT token with the given claims and subject.
     *
     * @param claims the claims to include in the token
     * @param subject the subject (usually username) for the token
     * @return the created JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Validates a JWT token for a specific username.
     * Checks token format, expiration, and username match.
     *
     * @param token the JWT token to validate
     * @param username the username to validate against
     * @return true if token is valid for the given username, false otherwise
     */
    public Boolean validateToken(String token, String username) {
        try {
            if (token == null || token.isBlank()) {
                log.debug("Token validation failed: token is null or blank");
                return false;
            }

            // Remove all whitespace
            String cleanToken = token.replaceAll("\\s+", "");

            if (cleanToken.isBlank()) {
                log.debug("Token validation failed: token contains only whitespace");
                return false;
            }

            // Pre-validate JWT format before attempting to extract username
            if (!isValidJwtFormat(cleanToken)) {
                log.debug("Token validation failed: invalid JWT format");
                return false;
            }

            final String extractedUsername = extractUsername(cleanToken);
            boolean isValid = extractedUsername.equals(username) && !isTokenExpired(cleanToken);

            if (!isValid) {
                log.debug("Token validation failed: username mismatch or token expired");
            }

            return isValid;
        } catch (Exception e) {
            log.debug("Token validation error: {}", e.getMessage());
            return false;
        }
    }
}


