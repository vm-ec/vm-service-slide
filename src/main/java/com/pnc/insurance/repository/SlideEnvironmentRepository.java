package com.pnc.insurance.repository;

import com.pnc.insurance.model.SlideEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideEnvironmentRepository extends JpaRepository<SlideEnvironment, Long> {
}

