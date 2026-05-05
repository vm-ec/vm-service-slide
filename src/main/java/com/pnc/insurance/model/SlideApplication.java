package com.pnc.insurance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "applications")
public class SlideApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name ="description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"application"})
    private List<Section> sections;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"sections", "urls"})
    private List<UrlRequest> urls;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<UrlRequest> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlRequest> urls) {
        this.urls = urls;
    }

    public SlideApplication() {}

    public SlideApplication(Long id, String name, String description, List<Section> sections, List<UrlRequest> urls) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sections = sections;
        this.urls = urls;
    }
}
