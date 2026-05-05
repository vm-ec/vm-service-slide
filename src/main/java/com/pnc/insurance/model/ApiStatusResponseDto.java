package com.pnc.insurance.model;

import java.io.Serializable;

public class ApiStatusResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String applicationStatus;
    private String sectionStatus;
    private String environmentStatus;
    private String tileStatus;
    private String urlStatus;

    private String status;
    private long executionTimeMs;

    // Getters and Setters

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
}