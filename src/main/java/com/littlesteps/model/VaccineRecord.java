package com.littlesteps.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vaccine_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccineRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Column(nullable = false)
    private String name;

    @Column
    private String dose;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "administered_date")
    private LocalDate administeredDate;

    @Column(nullable = false)
    @Builder.Default
    private String status = "Pending";

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
