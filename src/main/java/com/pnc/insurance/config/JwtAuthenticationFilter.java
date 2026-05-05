package com.pnc.insurance.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Extract token and remove all whitespace (including newlines, tabs, etc.)
            String rawToken = authHeader.substring(7);
            jwt = rawToken.replaceAll("\\s+", "");

            // Validate token format before processing
            if (jwt.isBlank()) {
                log.debug("Authorization header has empty token");
                filterChain.doFilter(request, response);
                return;
            }

            // Check if token has valid JWT format (3 parts separated by dots)
            if (!jwt.matches(JwtConstants.JWT_PATTERN)) {
                log.warn("Invalid JWT format: token does not match JWT pattern");
                filterChain.doFilter(request, response);
                return;
            }

            userEmail = jwtUtil.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("User {} authenticated successfully", userEmail);
                } else {
                    log.debug("Token validation failed for user {}", userEmail);
                }
            }

            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.security.SecurityException | io.jsonwebtoken.MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.warn("Unsupported JWT token: {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            log.warn("JWT validation error: {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.warn("Authentication error: {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}
