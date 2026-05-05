package com.pnc.insurance.controller;

import com.pnc.insurance.model.UrlRequest;
import com.pnc.insurance.model.UrlResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pnc.insurance.service.UrlParallelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@RestController
@RequestMapping("/api/urls-parallel")
public class UrlParallelController {

    private final UrlParallelService urlParallelService;

    @Autowired
    public UrlParallelController(UrlParallelService urlParallelService) {
        this.urlParallelService = urlParallelService;
    }

    @Operation(
        summary = "Fetch a list of URLs",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of URLs",
                content = @Content(schema = @Schema(implementation = String.class)))
        }
    )
    @GetMapping("/fetch")
    public ResponseEntity<List<String>> fetchUrls() {
        return ResponseEntity.ok(urlParallelService.fetchUrls());
    }

    @Operation(
        summary = "Call all URLs in parallel and return their statuses",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = UrlRequest.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Results for each URL",
                content = @Content(schema = @Schema(implementation = UrlResponseDto.class)))
        }
    )
    @PostMapping("/call")
    public ResponseEntity<List<UrlResponseDto>> callUrlsInParallel(@Valid @RequestBody UrlRequest request) {
        return ResponseEntity.ok(urlParallelService.callUrlsInParallel(request.getUrls()));
    }

    // Logic moved to service layer for separation of concerns
}

