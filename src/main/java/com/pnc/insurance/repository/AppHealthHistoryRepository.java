package com.pnc.insurance.repository;

import com.pnc.insurance.model.AppHealthHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppHealthHistoryRepository extends JpaRepository<AppHealthHistory, Long> {
}

