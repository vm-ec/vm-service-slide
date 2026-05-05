package com.pnc.insurance.controller;

import com.pnc.insurance.model.UrlResponseDto;
import com.pnc.insurance.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for dashboard operations to fetch data for display.
 * Provides paginated access to URL data for dashboard visualization.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "APIs for dashboard data retrieval with pagination support")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Retrieves all URLs for the dashboard with pagination support.
     *
     * @param pageable pagination information (default page size: 10)
     * @return ResponseEntity containing a paginated list of UrlResponseDto
     */
    @GetMapping("/urls")
    @Operation(summary = "Get all URLs", description = "Retrieves a paginated list of all URLs for dashboard display.")
    @ApiResponse(responseCode = "200", description = "URLs retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<UrlResponseDto>> getAllUrls(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(dashboardService.getAllUrls(pageable));
    }
}


