package com.pnc.insurance.controller;

import com.pnc.insurance.model.*;
import com.pnc.insurance.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing admin operations including URL requests, tiles, applications, sections, and environments.
 * Provides CRUD endpoints for various slide deck components.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Operations", description = "APIs for managing URL requests, tiles, applications, sections, and environments")
public class AdminController {

    private final UrlRequestService urlRequestService;
    private final TileService tileService;
    private final ApplicationService applicationService;
    private final SectionService sectionService;
    private final EnvironmentService environmentService;

    // ============ URL Requests endpoints ============

    @GetMapping("/url-requests")
    @Operation(summary = "Get all URL requests", description = "Retrieves a list of all URL requests.")
    @ApiResponse(responseCode = "200", description = "List of URL requests", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlResponseDto.class)))
    public ResponseEntity<List<UrlResponseDto>> getAllUrlRequests() {
        return ResponseEntity.ok(urlRequestService.getAllUrlRequests());
    }

    @GetMapping("/url-requests/{id}")
    @Operation(summary = "Get URL request by ID", description = "Retrieves a specific URL request by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "URL request found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "URL request not found")
    })
    public ResponseEntity<UrlResponseDto> getUrlRequestById(@PathVariable Long id) {
        UrlResponseDto dto = urlRequestService.getUrlRequestById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/url-requests")
    @Operation(summary = "Create URL request", description = "Creates a new URL request.")
    @ApiResponse(responseCode = "201", description = "URL request created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlResponseDto.class)))
    public ResponseEntity<UrlResponseDto> createUrlRequest(@RequestBody UrlRequest urlRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlRequestService.saveUrlRequest(urlRequest));
    }

    @PutMapping("/url-requests/{id}")
    @Operation(summary = "Update URL request", description = "Updates an existing URL request by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "URL request updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "URL request not found")
    })
    public ResponseEntity<UrlResponseDto> updateUrlRequest(@PathVariable Long id, @RequestBody UrlRequest urlRequest) {
        UrlResponseDto dto = urlRequestService.updateUrlRequest(id, urlRequest);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/url-requests/{id}")
    @Operation(summary = "Delete URL request", description = "Deletes a URL request by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "URL request deleted"),
        @ApiResponse(responseCode = "404", description = "URL request not found")
    })
    public ResponseEntity<String> deleteUrlRequest(@PathVariable Long id) {
        try {
            urlRequestService.deleteUrlRequest(id);
            return ResponseEntity.ok("URL deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ============ Tiles endpoints ============

    @GetMapping("/tiles")
    @Operation(summary = "Get all tiles", description = "Retrieves a list of all tiles.")
    @ApiResponse(responseCode = "200", description = "List of tiles", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tile.class)))
    public ResponseEntity<List<Tile>> getAllTiles() {
        return ResponseEntity.ok(tileService.getAllTiles());
    }

    @GetMapping("/tiles/{id}")
    @Operation(summary = "Get tile by ID", description = "Retrieves a specific tile by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tile found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tile.class))),
        @ApiResponse(responseCode = "404", description = "Tile not found")
    })
    public ResponseEntity<Tile> getTileById(@PathVariable Long id) {
        Tile tile = tileService.getTileById(id);
        return tile != null ? ResponseEntity.ok(tile) : ResponseEntity.notFound().build();
    }

    @PostMapping("/tiles")
    @Operation(summary = "Create tile", description = "Creates a new tile.")
    @ApiResponse(responseCode = "201", description = "Tile created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tile.class)))
    public ResponseEntity<Tile> createTile(@RequestBody Tile tile) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tileService.saveTile(tile));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/tiles/{id}")
    @Operation(summary = "Update tile", description = "Updates an existing tile by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tile updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tile.class))),
        @ApiResponse(responseCode = "404", description = "Tile not found")
    })
    public ResponseEntity<Tile> updateTile(@PathVariable Long id, @RequestBody Tile tile) {
        Tile updated = tileService.updateTile(id, tile);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tiles/{id}")
    @Operation(summary = "Delete tile", description = "Deletes a tile by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tile deleted"),
        @ApiResponse(responseCode = "404", description = "Tile not found")
    })
    public ResponseEntity<String> deleteTile(@PathVariable Long id) {
        return tileService.deleteTile(id) ? ResponseEntity.ok("Tile deleted successfully") : ResponseEntity.notFound().build();
    }

    // ============ Applications endpoints ============

    @GetMapping("/applications")
    @Operation(summary = "Get all applications", description = "Retrieves a list of all applications.")
    @ApiResponse(responseCode = "200", description = "List of applications", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideApplication.class)))
    public ResponseEntity<List<SlideApplication>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/applications/{id}")
    @Operation(summary = "Get application by ID", description = "Retrieves a specific application by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Application found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideApplication.class))),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public ResponseEntity<SlideApplication> getApplicationById(@PathVariable Long id) {
        SlideApplication application = applicationService.getApplicationById(id);
        return application != null ? ResponseEntity.ok(application) : ResponseEntity.notFound().build();
    }

    @PostMapping("/applications")
    @Operation(summary = "Create application", description = "Creates a new application.")
    @ApiResponse(responseCode = "201", description = "Application created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideApplication.class)))
    public ResponseEntity<SlideApplication> createApplication(@RequestBody SlideApplication application) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.saveApplication(application));
    }

    @PutMapping("/applications/{id}")
    @Operation(summary = "Update application", description = "Updates an existing application by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Application updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideApplication.class))),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public ResponseEntity<SlideApplication> updateApplication(@PathVariable Long id, @RequestBody SlideApplication application) {
        SlideApplication updated = applicationService.updateApplication(id, application);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/applications/{id}")
    @Operation(summary = "Delete application", description = "Deletes an application by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Application deleted"),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        return applicationService.deleteApplication(id) ? ResponseEntity.ok("Application deleted successfully") : ResponseEntity.notFound().build();
    }

    // ============ Sections endpoints ============

    @GetMapping("/sections")
    @Operation(summary = "Get all sections", description = "Retrieves a list of all sections.")
    @ApiResponse(responseCode = "200", description = "List of sections", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Section.class)))
    public ResponseEntity<List<Section>> getAllSections() {
        return ResponseEntity.ok(sectionService.getAllSections());
    }

    @GetMapping("/sections/{id}")
    @Operation(summary = "Get section by ID", description = "Retrieves a specific section by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Section found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Section.class))),
        @ApiResponse(responseCode = "404", description = "Section not found")
    })
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        Section section = sectionService.getSectionById(id);
        return section != null ? ResponseEntity.ok(section) : ResponseEntity.notFound().build();
    }

    @PostMapping("/sections")
    @Operation(summary = "Create section", description = "Creates a new section.")
    @ApiResponse(responseCode = "201", description = "Section created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Section.class)))
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.saveSection(section));
    }

    @PutMapping("/sections/{id}")
    @Operation(summary = "Update section", description = "Updates an existing section by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Section updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Section.class))),
        @ApiResponse(responseCode = "404", description = "Section not found")
    })
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody Section section) {
        Section updated = sectionService.updateSection(id, section);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/sections/{id}")
    @Operation(summary = "Delete section", description = "Deletes a section by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Section deleted"),
        @ApiResponse(responseCode = "404", description = "Section not found")
    })
    public ResponseEntity<String> deleteSection(@PathVariable Long id) {
        return sectionService.deleteSection(id) ? ResponseEntity.ok("Section deleted successfully") : ResponseEntity.notFound().build();
    }

    // ============ Environments endpoints ============

    @GetMapping("/environments")
    @Operation(summary = "Get all environments", description = "Retrieves a list of all environments.")
    @ApiResponse(responseCode = "200", description = "List of environments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideEnvironment.class)))
    public ResponseEntity<List<SlideEnvironment>> getAllEnvironments() {
        return ResponseEntity.ok(environmentService.getAllEnvironments());
    }

    @GetMapping("/environments/{id}")
    @Operation(summary = "Get environment by ID", description = "Retrieves a specific environment by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environment found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideEnvironment.class))),
        @ApiResponse(responseCode = "404", description = "Environment not found")
    })
    public ResponseEntity<SlideEnvironment> getEnvironmentById(@PathVariable Long id) {
        SlideEnvironment environment = environmentService.getEnvironmentById(id);
        return environment != null ? ResponseEntity.ok(environment) : ResponseEntity.notFound().build();
    }

    @PostMapping("/environments")
    @Operation(summary = "Create environment", description = "Creates a new environment.")
    @ApiResponse(responseCode = "201", description = "Environment created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideEnvironment.class)))
    public ResponseEntity<SlideEnvironment> createEnvironment(@RequestBody SlideEnvironment environment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(environmentService.saveEnvironment(environment));
    }

    @PutMapping("/environments/{id}")
    @Operation(summary = "Update environment", description = "Updates an existing environment by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environment updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SlideEnvironment.class))),
        @ApiResponse(responseCode = "404", description = "Environment not found")
    })
    public ResponseEntity<SlideEnvironment> updateEnvironment(@PathVariable Long id, @RequestBody SlideEnvironment environment) {
        SlideEnvironment updated = environmentService.updateEnvironment(id, environment);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/environments/{id}")
    @Operation(summary = "Delete environment", description = "Deletes an environment by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environment deleted"),
        @ApiResponse(responseCode = "404", description = "Environment not found")
    })
    public ResponseEntity<String> deleteEnvironment(@PathVariable Long id) {
        return environmentService.deleteEnvironment(id) ? ResponseEntity.ok("Environment deleted successfully") : ResponseEntity.notFound().build();
    }

}