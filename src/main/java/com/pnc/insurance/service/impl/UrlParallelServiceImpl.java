package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.UrlResponseDto;
import com.pnc.insurance.service.UrlParallelService;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        List<CompletableFuture<UrlResponseDto>> futures = urls.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> callUrl(url)))
                .toList();
        return futures.stream().map(CompletableFuture::join).toList();
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
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int status = conn.getResponseCode();
            dto.setStatus(status);

            // Read response body
            String responseBody = readResponseBody(conn, status);
            dto.setBody(responseBody);

            conn.disconnect();
        } catch (Exception e) {
            dto.setStatus(-1);
            dto.setBody("Error");
            dto.setDescription(e.getMessage());
        }
        return dto;
    }

    private String readResponseBody(HttpURLConnection conn, int status) {
        try {
            BufferedReader reader;
            if (status >= 400) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();

            String body = responseBody.toString();
            return body.isEmpty() ? "Success" : body;
        } catch (Exception e) {
            return "Response read error: " + e.getMessage();
        }
    }
}

