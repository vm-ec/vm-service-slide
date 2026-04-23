package com.example.pnc_insurance.repository;

import com.example.pnc_insurance.model.UrlRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRequestRepository extends JpaRepository<UrlRequest, Long> {
}

