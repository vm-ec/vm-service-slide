package com.example.pnc_insurance.repository;

import com.example.pnc_insurance.model.SlideApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideApplicationRepository extends JpaRepository<SlideApplication, Long> {
}

