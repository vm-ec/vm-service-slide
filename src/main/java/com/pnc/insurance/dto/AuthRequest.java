package com.pnc.insurance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for authentication request.
 * Contains username and password for user login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication request containing user credentials")
public class AuthRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username for authentication", example = "user@example.com")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password for authentication", example = "password123")
    private String password;
}

