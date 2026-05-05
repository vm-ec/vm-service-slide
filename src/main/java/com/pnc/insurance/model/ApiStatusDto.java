package com.pnc.insurance.model;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for API Status Response containing counts, data and UP/DOWN status
 */
public class ApiStatusDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // Counts
    private int applicationCount;
    private int sectionCount;
    private int environmentCount;
    private int tileCount;
    private int urlCount;

    // Data
    private List<SlideApplication> applications;
    private List<Section> sections;
    private List<SlideEnvironment> environments;
    private List<Tile> tiles;
    private List<UrlResponseDto> urls;

    // Overall status
    private String status;
    private long executionTimeMs;

    // 🔥 NEW: Individual service health status
    private String applicationStatus;
    private String sectionStatus;
    private String environmentStatus;
    private String tileStatus;
    private String urlStatus;

    // Constructors
    public ApiStatusDto() {}

    public ApiStatusDto(int applicationCount, int sectionCount, int environmentCount,
                        int tileCount, int urlCount, String status, long executionTimeMs) {
        this.applicationCount = applicationCount;
        this.sectionCount = sectionCount;
        this.environmentCount = environmentCount;
        this.tileCount = tileCount;
        this.urlCount = urlCount;
        this.status = status;
        this.executionTimeMs = executionTimeMs;
    }

    // Getters and Setters

    public int getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(int applicationCount) {
        this.applicationCount = applicationCount;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int getEnvironmentCount() {
        return environmentCount;
    }

    public void setEnvironmentCount(int environmentCount) {
        this.environmentCount = environmentCount;
    }

    public int getTileCount() {
        return tileCount;
    }

    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    public int getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(int urlCount) {
        this.urlCount = urlCount;
    }

    public List<SlideApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<SlideApplication> applications) {
        this.applications = applications;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<SlideEnvironment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<SlideEnvironment> environments) {
        this.environments = environments;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<UrlResponseDto> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlResponseDto> urls) {
        this.urls = urls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    // 🔥 NEW STATUS GETTERS/SETTERS

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getSectionStatus() {
        return sectionStatus;
    }

    public void setSectionStatus(String sectionStatus) {
        this.sectionStatus = sectionStatus;
    }

    public String getEnvironmentStatus() {
        return environmentStatus;
    }

    public void setEnvironmentStatus(String environmentStatus) {
        this.environmentStatus = environmentStatus;
    }

    public String getTileStatus() {
        return tileStatus;
    }

    public void setTileStatus(String tileStatus) {
        this.tileStatus = tileStatus;
    }

    public String getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(String urlStatus) {
        this.urlStatus = urlStatus;
    }
}