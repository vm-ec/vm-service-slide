package com.pnc.insurance.repository;

import com.pnc.insurance.model.UrlRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRequestRepository extends JpaRepository<UrlRequest, Long> {
}

