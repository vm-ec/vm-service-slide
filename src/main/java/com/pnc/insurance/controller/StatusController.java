package com.pnc.insurance.controller;

import com.pnc.insurance.model.ApiStatusResponseDto;
import com.pnc.insurance.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for retrieving API status and health information.
 * Provides comprehensive status of all APIs including applications, sections, environments, tiles, and URLs.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
@Tag(name = "API Status", description = "APIs for retrieving overall API health status and detailed service information")
public class StatusController {

    private final StatusService statusService;

    /**
     * Retrieves the status of all APIs.
     * Returns comprehensive status information including applications, sections, environments, tiles, and URLs.
     *
     * @return ResponseEntity containing the ApiStatusResponseDto with complete status information
     */
    @GetMapping("/all")
    @Operation(summary = "Get all API status",
            description = "Retrieves comprehensive status of all APIs including applications, sections, environments, tiles, and URLs")
    @ApiResponse(responseCode = "200", description = "Status retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiStatusResponseDto.class)))
    public ResponseEntity<ApiStatusResponseDto> getAllApiStatus() {
        return ResponseEntity.ok(statusService.getAllApiStatus());
    }
}

