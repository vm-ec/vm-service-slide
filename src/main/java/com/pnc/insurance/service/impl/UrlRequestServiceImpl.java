package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.*;
import com.pnc.insurance.repository.*;
import com.pnc.insurance.service.UrlRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlRequestServiceImpl implements UrlRequestService {

    private final UrlRequestRepository urlRepo;
    private final SlideApplicationRepository appRepo;
    private final SlideEnvironmentRepository envRepo;
    private final SectionRepository sectionRepo;

    public UrlRequestServiceImpl(
            UrlRequestRepository urlRepo,
            SlideApplicationRepository appRepo,
            SlideEnvironmentRepository envRepo,
            SectionRepository sectionRepo) {
        this.urlRepo = urlRepo;
        this.appRepo = appRepo;
        this.envRepo = envRepo;
        this.sectionRepo = sectionRepo;
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
    public UrlResponseDto saveUrlRequest(UrlRequest request) {

        UrlRequest url = new UrlRequest();
        mapRequestToEntity(request, url);

        return mapToDto(urlRepo.save(url));
    }

    // ✅ UPDATE
    @Override
    public UrlResponseDto updateUrlRequest(Long id, UrlRequest request) {

        UrlRequest existing = urlRepo.findById(id).orElse(null);

        if (existing == null) {
            return null; // or throw if needed
        }

        mapRequestToEntity(request, existing);

        return mapToDto(urlRepo.save(existing));
    }

    // ✅ DELETE
    @Override
    public boolean deleteUrlRequest(Long id) {

        UrlRequest url = urlRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        // Optional: clear child relations safely
        if (url.getHealthHistory() != null) {
            url.getHealthHistory().clear();
        }

        urlRepo.delete(url);
        return false;
    }

    // ===============================
    // 🔁 MAPPING METHODS (NO VALIDATION)
    // ===============================

    private void mapRequestToEntity(UrlRequest request, UrlRequest entity) {

        entity.setBaseUrl(request.getBaseUrl());
        entity.setTile(request.getTile());
        entity.setDescription(request.getDescription());

        // ✅ Application (no exception)
        if (request.getApplication() != null && request.getApplication().getId() != null) {
            appRepo.findById(request.getApplication().getId())
                    .ifPresent(entity::setApplication);
        }

        // ✅ Environment (no exception)
        if (request.getEnvironment() != null && request.getEnvironment().getId() != null) {
            envRepo.findById(request.getEnvironment().getId())
                    .ifPresent(entity::setEnvironment);
        }

        // ✅ Section (no exception)
        if (request.getSection() != null && request.getSection().getId() != null) {
            sectionRepo.findById(request.getSection().getId())
                    .ifPresent(entity::setSection);
        }
    }

    private UrlResponseDto mapToDto(UrlRequest url) {

        UrlResponseDto dto = new UrlResponseDto();

        dto.setId(url.getId());
        dto.setBaseUrl(url.getBaseUrl());
        dto.setTile(url.getTile());
        dto.setDescription(url.getDescription());

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
}
