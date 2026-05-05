package com.pnc.insurance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for authentication response.
 * Contains the JWT token after successful authentication.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication response containing JWT token")
public class AuthResponse {

    @Schema(description = "JWT token for authenticated requests", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}

