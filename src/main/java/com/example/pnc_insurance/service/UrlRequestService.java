package com.example.pnc_insurance.service;

import com.example.pnc_insurance.model.UrlRequest;
import com.example.pnc_insurance.model.UrlResponseDto;

import java.util.List;

public interface UrlRequestService {

    List<UrlResponseDto> getAllUrlRequests();

    UrlResponseDto getUrlRequestById(Long id);

    UrlResponseDto saveUrlRequest(UrlRequest urlRequest);

    UrlResponseDto updateUrlRequest(Long id, UrlRequest urlRequest);

    boolean deleteUrlRequest(Long id);}