package com.example.pnc_insurance.model;

import com.example.pnc_insurance.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_health_history")
public class AppHealthHistory {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name = "url_id", nullable = false)
private UrlRequest url;

@Enumerated(EnumType.STRING)
private Status status; // UP / DOWN

private LocalDateTime timestamp;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UrlRequest getUrl() {
        return url;
    }

    public void setUrl(UrlRequest url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
