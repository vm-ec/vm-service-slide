package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.UrlResponseDto;
import com.pnc.insurance.service.UrlParallelService;
import org.springframework.stereotype.Service;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UrlParallelServiceImpl implements UrlParallelService {
    @Override
    public List<String> fetchUrls() {
        return List.of(
                "https://www.google.com",
                "https://www.github.com",
                "https://www.stackoverflow.com"
        );
    }

    @Override
    public List<UrlResponseDto> callUrlsInParallel(List<String> urls) {
        List<CompletableFuture<UrlResponseDto>> futures = urls.parallelStream()
                .map(url -> CompletableFuture.supplyAsync(() -> callUrl(url)))
                .collect(Collectors.toList());
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private UrlResponseDto callUrl(String urlStr) {
        UrlResponseDto dto = new UrlResponseDto();
        dto.setBaseUrl(urlStr);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            dto.setStatus(status);
            dto.setBody("OK");
        } catch (Exception e) {
            dto.setStatus(-1);
            dto.setBody(null);
            dto.setDescription(e.getMessage());
        }
        return dto;
    }
}

