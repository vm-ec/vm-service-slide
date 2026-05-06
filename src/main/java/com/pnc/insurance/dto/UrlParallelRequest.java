package com.pnc.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * DTO for parallel URL call requests.
 * This DTO is specifically designed for the parallel URL call endpoint.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlParallelRequest {
    private List<String> urls;
}

