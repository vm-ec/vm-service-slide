package com.pnc.insurance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for error responses.
 * Standardized format for all API error responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Standard error response")
public class ErrorResponse {

    @Schema(description = "Error message", example = "Invalid username or password")
    private String message;

    @Schema(description = "HTTP status code", example = "401")
    private int status;

    @Schema(description = "Timestamp when error occurred", example = "1682678400000")
    private long timestamp;

    @Schema(description = "Error code for client identification", example = "AUTH_ERROR")
    private String code;

    /**
     * Constructor with message only - sets timestamp to current time
     */
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Constructor with message and status - sets timestamp to current time
     */
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
}

