package com.pnc.insurance.service;

import com.pnc.insurance.dto.UrlRequestCreateDto;
import com.pnc.insurance.model.UrlRequest;
import com.pnc.insurance.model.UrlResponseDto;

import java.util.List;

public interface UrlRequestService {

    List<UrlResponseDto> getAllUrlRequests();

    UrlResponseDto getUrlRequestById(Long id);

    UrlResponseDto saveUrlRequest(UrlRequestCreateDto requestDto);

    UrlResponseDto updateUrlRequest(Long id, UrlRequest urlRequest);

    boolean deleteUrlRequest(Long id);

    // ✅ Check and update URL status
    int checkAndUpdateUrlStatus(Long id);
}
