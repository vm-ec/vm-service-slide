package com.pnc.insurance.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "slide_environment")
@Data
public class SlideEnvironment {
    @Id
    private Long id;

    private String name;     // DEV, QA, PROD
    private String region;   // US-East, India, etc.

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UrlRequest> urls;
}
