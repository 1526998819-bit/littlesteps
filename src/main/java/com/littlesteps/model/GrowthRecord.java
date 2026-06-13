package com.littlesteps.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "growth_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column
    private Double height;

    @Column
    private Double weight;

    @Column(name = "head_circumference")
    private Double headCircumference;

    @Column
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
