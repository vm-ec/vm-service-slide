package com.example.pnc_insurance.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tiles")
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tile_id", unique = true, nullable = false)
    private String tileId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}