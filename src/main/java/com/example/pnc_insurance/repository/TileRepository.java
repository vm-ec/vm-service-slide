package com.example.pnc_insurance.repository;

import com.example.pnc_insurance.model.Tile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TileRepository extends JpaRepository<Tile, Long> {
}

