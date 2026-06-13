package com.littlesteps.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gallery_photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Column(nullable = false, length = 500)
    private String url;

    @Column
    private String caption;

    @Column
    @Builder.Default
    private String type = "photo";

    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (takenAt == null) {
            takenAt = LocalDateTime.now();
        }
    }
}
