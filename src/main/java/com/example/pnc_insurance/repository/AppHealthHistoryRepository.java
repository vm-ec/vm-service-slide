package com.example.pnc_insurance.repository;

import com.example.pnc_insurance.model.AppHealthHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppHealthHistoryRepository extends JpaRepository<AppHealthHistory, Long> {
}

