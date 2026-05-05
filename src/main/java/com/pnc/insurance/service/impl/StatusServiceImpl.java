package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.ApiStatusResponseDto;
import com.pnc.insurance.model.Section;
import com.pnc.insurance.model.SlideApplication;
import com.pnc.insurance.model.SlideEnvironment;
import com.pnc.insurance.model.Tile;
import com.pnc.insurance.model.UrlResponseDto;
import com.pnc.insurance.service.ApplicationService;
import com.pnc.insurance.service.EnvironmentService;
import com.pnc.insurance.service.SectionService;
import com.pnc.insurance.service.StatusService;
import com.pnc.insurance.service.TileService;
import com.pnc.insurance.service.UrlRequestService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StatusServiceImpl implements StatusService {

    private final ApplicationService applicationService;
    private final SectionService sectionService;
    private final EnvironmentService environmentService;
    private final TileService tileService;
    private final UrlRequestService urlRequestService;

    public StatusServiceImpl(ApplicationService applicationService,
                             SectionService sectionService,
                             EnvironmentService environmentService,
                             TileService tileService,
                             UrlRequestService urlRequestService) {
        this.applicationService = applicationService;
        this.sectionService = sectionService;
        this.environmentService = environmentService;
        this.tileService = tileService;
        this.urlRequestService = urlRequestService;
    }

    @Override
    public ApiStatusResponseDto getAllApiStatus() {

        long startTime = System.currentTimeMillis();

        String[] applicationStatus = {"UP"};
        String[] sectionStatus = {"UP"};
        String[] environmentStatus = {"UP"};
        String[] tileStatus = {"UP"};
        String[] urlStatus = {"UP"};

        // APPLICATIONS
        CompletableFuture<List<SlideApplication>> applicationsF =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return applicationService.getAllApplications();
                    } catch (Exception e) {
                        applicationStatus[0] = "DOWN";
                        return Collections.emptyList();
                    }
                });

        // SECTIONS
        CompletableFuture<List<Section>> sectionsF =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return sectionService.getAllSections();
                    } catch (Exception e) {
                        sectionStatus[0] = "DOWN";
                        return Collections.emptyList();
                    }
                });

        // ENVIRONMENTS
        CompletableFuture<List<SlideEnvironment>> envF =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return environmentService.getAllEnvironments();
                    } catch (Exception e) {
                        environmentStatus[0] = "DOWN";
                        return Collections.emptyList();
                    }
                });

        // TILES
        CompletableFuture<List<Tile>> tilesF =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return tileService.getAllTiles();
                    } catch (Exception e) {
                        tileStatus[0] = "DOWN";
                        return Collections.emptyList();
                    }
                });

        // URLS
        CompletableFuture<List<UrlResponseDto>> urlsF =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return urlRequestService.getAllUrlRequests();
                    } catch (Exception e) {
                        urlStatus[0] = "DOWN";
                        return Collections.emptyList();
                    }
                });

        // Wait for all to complete
        CompletableFuture.allOf(
                applicationsF,
                sectionsF,
                envF,
                tilesF,
                urlsF
        ).join();

        // Build response DTO
        ApiStatusResponseDto dto = new ApiStatusResponseDto();

        dto.setApplicationStatus(applicationStatus[0]);
        dto.setSectionStatus(sectionStatus[0]);
        dto.setEnvironmentStatus(environmentStatus[0]);
        dto.setTileStatus(tileStatus[0]);
        dto.setUrlStatus(urlStatus[0]);

        dto.setStatus("OK");
        dto.setExecutionTimeMs(System.currentTimeMillis() - startTime);

        return dto;
    }
}