package com.pnc.insurance.service;

import com.pnc.insurance.model.Tile;

import java.util.List;

public interface TileService {

    List<Tile> getAllTiles();

    Tile getTileById(Long id);

    Tile saveTile(Tile tile);

    Tile updateTile(Long id, Tile tile);

    Tile patchTile(Long id, Tile tile);

    boolean deleteTile(Long id);
}
