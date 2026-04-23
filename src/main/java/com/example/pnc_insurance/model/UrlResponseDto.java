package com.example.pnc_insurance.model;

public class UrlResponseDto {

    private Long id;
    private String baseUrl;
    private String tile;
    private String description;

    private Long applicationId;
    private String applicationName;

    private Long environmentId;
    private String environmentName;

    private Long sectionId;
    private String sectionName;

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getTile() {
        return tile;
    }

    public String getDescription() {
        return description;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}