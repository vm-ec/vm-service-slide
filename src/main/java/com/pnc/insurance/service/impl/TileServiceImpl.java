package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.Section;
import com.pnc.insurance.model.Tile;
import com.pnc.insurance.repository.SectionRepository;
import com.pnc.insurance.repository.TileRepository;
import com.pnc.insurance.service.TileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TileServiceImpl implements TileService {

    private final TileRepository tileRepository;
    private final SectionRepository sectionRepository;

    public TileServiceImpl(TileRepository tileRepository, SectionRepository sectionRepository) {
        this.tileRepository = tileRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Tile> getAllTiles() {
        return tileRepository.findAll();
    }

    @Override
    public Tile getTileById(Long id) {
        return tileRepository.findById(id).orElse(null);
    }

    @Override
    public Tile saveTile(Tile tile) {
        // Validate section if provided
        if (tile.getSection() != null && tile.getSection().getId() != null) {
            Section section = sectionRepository.findById(tile.getSection().getId()).orElse(null);
            if (section == null) {
                throw new RuntimeException("Section not found");
            }
            tile.setSection(section);
        }
        return tileRepository.save(tile);
    }

    @Override
    public Tile updateTile(Long id, Tile tile) {
        Tile existing = tileRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setTileId(tile.getTileId());
        existing.setName(tile.getName());
        if (tile.getSection() != null && tile.getSection().getId() != null) {
            Section section = sectionRepository.findById(tile.getSection().getId()).orElse(null);
            if (section == null) {
                throw new RuntimeException("Section not found");
            }
            existing.setSection(section);
        }
        return tileRepository.save(existing);
    }

    @Override
    public Tile patchTile(Long id, Tile tile) {
        Tile existing = tileRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        if (tile.getTileId() != null) {
            existing.setTileId(tile.getTileId());
        }
        if (tile.getName() != null) {
            existing.setName(tile.getName());
        }
        if (tile.getSection() != null && tile.getSection().getId() != null) {
            Section section = sectionRepository.findById(tile.getSection().getId()).orElse(null);
            if (section == null) {
                throw new RuntimeException("Section not found");
            }
            existing.setSection(section);
        }
        return tileRepository.save(existing);
    }

    @Override
    public boolean deleteTile(Long id) {
        if (!tileRepository.existsById(id)) {
            return false;
        }
        tileRepository.deleteById(id);
        return true;
    }
}
