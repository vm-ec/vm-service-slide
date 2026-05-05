package com.pnc.insurance.service;

import com.pnc.insurance.model.UrlRequest;
import com.pnc.insurance.model.UrlResponseDto;
import java.util.List;

public interface UrlParallelService {
    List<String> fetchUrls();
    List<UrlResponseDto> callUrlsInParallel(List<String> urls);
}

