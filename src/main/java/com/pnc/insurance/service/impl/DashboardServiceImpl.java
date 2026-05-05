package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.UrlResponseDto;
import com.pnc.insurance.service.DashboardService;
import com.pnc.insurance.service.UrlRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UrlRequestService urlRequestService;

    public DashboardServiceImpl(UrlRequestService urlRequestService) {
        this.urlRequestService = urlRequestService;
    }

    @Override
    public Page<UrlResponseDto> getAllUrls(Pageable pageable) {
        List<UrlResponseDto> allUrls = urlRequestService.getAllUrlRequests();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allUrls.size());
        List<UrlResponseDto> subList = allUrls.subList(start, end);
        return new PageImpl<>(subList, pageable, allUrls.size());
    }
}
