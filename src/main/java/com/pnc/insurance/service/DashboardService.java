package com.pnc.insurance.service;

import com.pnc.insurance.model.UrlResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DashboardService {

    Page<UrlResponseDto> getAllUrls(Pageable pageable);
}
