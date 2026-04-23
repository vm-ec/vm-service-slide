package com.example.pnc_insurance.controller;

import com.example.pnc_insurance.model.UrlRequest;
import com.example.pnc_insurance.model.UrlResponseDto;
import com.example.pnc_insurance.service.UrlRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/url-requests")
public class AdminController {

    @Autowired
    private UrlRequestService urlRequestService;

    @GetMapping
    public ResponseEntity<List<UrlResponseDto>> getAllUrlRequests() {
        return ResponseEntity.ok(urlRequestService.getAllUrlRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UrlResponseDto> getUrlRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(urlRequestService.getUrlRequestById(id));
    }

    @PostMapping
    public ResponseEntity<UrlResponseDto> createUrlRequest(@RequestBody UrlRequest urlRequest) {
        return ResponseEntity.ok(urlRequestService.saveUrlRequest(urlRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UrlResponseDto> updateUrlRequest(@PathVariable Long id,
                                                           @RequestBody UrlRequest urlRequest) {
        return ResponseEntity.ok(urlRequestService.updateUrlRequest(id, urlRequest));
    }
    @DeleteMapping("/admin/url/{id}")
    public ResponseEntity<String> deleteUrlRequest(@PathVariable Long id) {

        urlRequestService.deleteUrlRequest(id);

        return ResponseEntity.ok("URL deleted successfully");
    }
}