package com.pnc.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating URL requests with proper relationship objects
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequestCreateDto {

    private String baseUrl;
    private String tile;
    private String description;
    private Integer status;

    // Relationship objects for proper deserialization
    private ApplicationRef application;
    private EnvironmentRef environment;
    private SectionRef section;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationRef {
        private Long id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentRef {
        private Long id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectionRef {
        private Long id;
    }
}
