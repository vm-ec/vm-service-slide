package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.*;
import com.pnc.insurance.repository.*;
import com.pnc.insurance.service.UrlRequestService;
import com.pnc.insurance.dto.UrlRequestCreateDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class UrlRequestServiceImpl implements UrlRequestService {

    private final UrlRequestRepository urlRepo;
    private final SlideApplicationRepository appRepo;
    private final SlideEnvironmentRepository envRepo;
    private final SectionRepository sectionRepo;
    private final RestTemplate restTemplate;

    public UrlRequestServiceImpl(
            UrlRequestRepository urlRepo,
            SlideApplicationRepository appRepo,
            SlideEnvironmentRepository envRepo,
            SectionRepository sectionRepo,
            RestTemplate restTemplate) {
        this.urlRepo = urlRepo;
        this.appRepo = appRepo;
        this.envRepo = envRepo;
        this.sectionRepo = sectionRepo;
        this.restTemplate = restTemplate;
    }

    // ✅ GET ALL
    @Override
    public List<UrlResponseDto> getAllUrlRequests() {
        return urlRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @Override
    public UrlResponseDto getUrlRequestById(Long id) {
        UrlRequest url = urlRepo.findById(id).orElse(null);

        if (url == null) {
            return null; // or throw if you prefer
        }

        return mapToDto(url);
    }

    // ✅ CREATE
    @Override
    @Transactional
    public UrlResponseDto saveUrlRequest(UrlRequestCreateDto requestDto) {

        UrlRequest url = new UrlRequest();
        mapDtoToEntity(requestDto, url);

        // ✅ Check initial status immediately when creating
        if (url.getStatus() == null || url.getStatus() == 0) {
            try {
                int initialStatus = checkUrlStatus(url.getBaseUrl());
                url.setStatus(initialStatus);
                System.out.println("✅ Initial status check for " + url.getTile() + " -> Status: " + initialStatus);
            } catch (Exception e) {
                System.err.println("❌ Failed initial status check for " + url.getTile() + ": " + e.getMessage());
                url.setStatus(404); // Default to unreachable if check fails
            }
        }

        UrlRequest savedUrl = urlRepo.save(url);
        System.out.println("✅ URL Request saved successfully - ID: " + savedUrl.getId() + ", Tile: " + savedUrl.getTile());

        // Re-fetch to ensure relationships are loaded
        return mapToDto(urlRepo.findById(savedUrl.getId()).orElse(savedUrl));
    }

    // ✅ UPDATE
    @Override
    @Transactional
    public UrlResponseDto updateUrlRequest(Long id, UrlRequest request) {

        UrlRequest existing = urlRepo.findById(id).orElse(null);

        if (existing == null) {
            return null; // Return null if not found (404)
        }

        // Store old URL for comparison
        String oldBaseUrl = existing.getBaseUrl();

        // Map request data to existing entity
        mapRequestToEntity(request, existing);

        // ✅ Re-check status if URL was updated
        if (oldBaseUrl != null && !oldBaseUrl.equals(existing.getBaseUrl())) {
            try {
                int updatedStatus = checkUrlStatus(existing.getBaseUrl());
                existing.setStatus(updatedStatus);
                System.out.println("✅ Status re-checked for updated URL " + existing.getTile() + " -> Status: " + updatedStatus);
            } catch (Exception e) {
                System.err.println("❌ Failed status re-check for updated URL " + existing.getTile() + ": " + e.getMessage());
            }
        }

        // Save and persist changes to database
        UrlRequest saved = urlRepo.save(existing);
        System.out.println("✅ URL Request updated successfully - ID: " + saved.getId() + ", Tile: " + saved.getTile() + ", URL: " + saved.getBaseUrl());

        return mapToDto(saved);
    }

    // ✅ DELETE
    @Override
    @Transactional
    public boolean deleteUrlRequest(Long id) {

        UrlRequest url = urlRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        // Optional: clear child relations safely
        if (url.getHealthHistory() != null) {
            url.getHealthHistory().clear();
        }

        urlRepo.delete(url);
        System.out.println("✅ URL Request deleted successfully - ID: " + id);
        return true;
    }

    // ===============================
    // 🔁 MAPPING METHODS (NO VALIDATION)
    // ===============================

    private void mapRequestToEntity(UrlRequest request, UrlRequest entity) {

        entity.setBaseUrl(request.getBaseUrl());
        entity.setTile(request.getTile());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());

        // ✅ Application - Check both nested object AND flat applicationId field
        if (request.getApplication() != null && request.getApplication().getId() != null) {
            // If nested object exists, use it
            appRepo.findById(request.getApplication().getId())
                    .ifPresent(entity::setApplication);
        } else if (request.getApplicationId() != null) {
            // Otherwise check for flat applicationId field (from UrlResponseDto)
            appRepo.findById(request.getApplicationId())
                    .ifPresent(entity::setApplication);
        }

        // ✅ Environment - Check both nested object AND flat environmentId field
        if (request.getEnvironment() != null && request.getEnvironment().getId() != null) {
            // If nested object exists, use it
            envRepo.findById(request.getEnvironment().getId())
                    .ifPresent(entity::setEnvironment);
        } else if (request.getEnvironmentId() != null) {
            // Otherwise check for flat environmentId field (from UrlResponseDto)
            try {
                Long envId = Long.valueOf(request.getEnvironmentId().toString());
                envRepo.findById(envId)
                        .ifPresent(entity::setEnvironment);
            } catch (Exception e) {
                System.err.println("⚠️ Invalid environmentId format: " + request.getEnvironmentId());
            }
        }

        // ✅ Section - Check both nested object AND flat sectionId field
        if (request.getSection() != null && request.getSection().getId() != null) {
            // If nested object exists, use it
            sectionRepo.findById(request.getSection().getId())
                    .ifPresent(entity::setSection);
        } else if (request.getSectionId() != null) {
            // Otherwise check for flat sectionId field (from UrlResponseDto)
            sectionRepo.findById(request.getSectionId())
                    .ifPresent(entity::setSection);
        }
    }

    private void mapDtoToEntity(UrlRequestCreateDto requestDto, UrlRequest entity) {

        entity.setBaseUrl(requestDto.getBaseUrl());
        entity.setTile(requestDto.getTile());
        entity.setDescription(requestDto.getDescription());
        entity.setStatus(requestDto.getStatus());

        // ✅ Application (no exception) - Check nested object
        if (requestDto.getApplication() != null && requestDto.getApplication().getId() != null) {
            appRepo.findById(requestDto.getApplication().getId())
                    .ifPresent(entity::setApplication);
        }

        // ✅ Environment (no exception) - Check nested object
        if (requestDto.getEnvironment() != null && requestDto.getEnvironment().getId() != null) {
            envRepo.findById(requestDto.getEnvironment().getId())
                    .ifPresent(entity::setEnvironment);
        }

        // ✅ Section (no exception) - Check nested object
        if (requestDto.getSection() != null && requestDto.getSection().getId() != null) {
            sectionRepo.findById(requestDto.getSection().getId())
                    .ifPresent(entity::setSection);
        }
    }

    private UrlResponseDto mapToDto(UrlRequest url) {

        UrlResponseDto dto = new UrlResponseDto();

        dto.setId(url.getId());
        dto.setBaseUrl(url.getBaseUrl());
        dto.setTile(url.getTile());
        dto.setDescription(url.getDescription());
        dto.setStatus(url.getStatus());

        if (url.getApplication() != null) {
            dto.setApplicationId(url.getApplication().getId());
            dto.setApplicationName(url.getApplication().getName());
        }

        if (url.getEnvironment() != null) {
            dto.setEnvironmentId(url.getEnvironment().getId());
            dto.setEnvironmentName(url.getEnvironment().getName());
        }

        if (url.getSection() != null) {
            dto.setSectionId(url.getSection().getId());
            dto.setSectionName(url.getSection().getName());
        }

        return dto;
    }

    // ===============================
    // ⏰ SCHEDULED STATUS UPDATES (Every 15 minutes)
    // ===============================

    /**
     * Scheduled task that runs every 15 minutes to update URL status
     * Uses parallel streams for efficient processing
     */
    @Scheduled(fixedRate = 900000) // 15 minutes = 900,000 milliseconds
    @Transactional
    public void updateAllUrlStatuses() {
        System.out.println("🔄 Starting scheduled URL status update...");

        List<UrlRequest> allUrls = urlRepo.findAll();

        if (allUrls.isEmpty()) {
            System.out.println("ℹ️ No URLs to check");
            return;
        }

        // Process URLs in parallel using streams
        List<CompletableFuture<Void>> futures = allUrls.parallelStream()
            .map(url -> CompletableFuture.runAsync(() -> {
                try {
                    int newStatus = checkUrlStatus(url.getBaseUrl());
                    url.setStatus(newStatus);
                    urlRepo.save(url);
                    System.out.println("✅ Updated " + url.getTile() + " -> Status: " + newStatus);
                } catch (Exception e) {
                    System.err.println("❌ Failed to update " + url.getTile() + ": " + e.getMessage());
                    // Keep existing status on error
                }
            }))
            .collect(Collectors.toList());

        // Wait for all parallel tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .join();

        System.out.println("🎉 Completed URL status update for " + allUrls.size() + " URLs");
    }

    /**
     * Check the HTTP status of a URL
     * @param url The URL to check
     * @return 200 if reachable, 404 if not reachable
     */
    private int checkUrlStatus(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK ? 200 : 404;
        } catch (Exception e) {
            // Any exception (connection timeout, DNS error, etc.) means unreachable
            return 404;
        }
    }

    // ✅ PUBLIC METHOD: Check and update URL status
    @Transactional
    public int checkAndUpdateUrlStatus(Long id) {
        UrlRequest url = urlRepo.findById(id).orElse(null);
        if (url == null) {
            throw new RuntimeException("URL not found");
        }

        try {
            int status = checkUrlStatus(url.getBaseUrl());
            url.setStatus(status);
            urlRepo.save(url);
            System.out.println("✅ Status check for " + url.getTile() + " -> Status: " + status);
            return status;
        } catch (Exception e) {
            System.err.println("❌ Failed status check for " + url.getTile() + ": " + e.getMessage());
            url.setStatus(404);
            urlRepo.save(url);
            return 404;
        }
    }
}
