package com.example.pnc_insurance.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "urls", uniqueConstraints = {
        @UniqueConstraint(columnNames = "tile")
})
@Transactional
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest extends UrlResponseDto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private SlideApplication application;

    @ManyToOne
    @JoinColumn(name = "environment_id")
    private SlideEnvironment environment;


    @Column(columnDefinition = "VARCHAR(255)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;


    @Column(name = "tile", unique = true, nullable = false)
    private String tile;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppHealthHistory> healthHistory;

}
