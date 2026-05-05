package com.pnc.insurance.repository;

import com.pnc.insurance.model.SlideApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideApplicationRepository extends JpaRepository<SlideApplication, Long> {
}

