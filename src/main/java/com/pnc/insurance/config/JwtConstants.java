package com.pnc.insurance.config;

/**
 * Constants used for JWT token generation and validation.
 */
public class JwtConstants {

    /**
     * Regular expression pattern for validating JWT format (3 parts separated by dots)
     */
    public static final String JWT_PATTERN = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+$";

    /**
     * Default JWT expiration time in milliseconds (24 hours)
     */
    public static final long JWT_EXPIRATION_DEFAULT = 86400000;

    private JwtConstants() {
        // Private constructor to prevent instantiation
    }
}

