package com.example.pnc_insurance.repository;

import com.example.pnc_insurance.model.SlideEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideEnvironmentRepository extends JpaRepository<SlideEnvironment, Long> {
}

