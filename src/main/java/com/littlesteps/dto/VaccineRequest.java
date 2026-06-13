package com.littlesteps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccineRequest {

    private Long childId;
    private String name;
    private String dose;
    private LocalDate scheduledDate;
    private LocalDate administeredDate;
    private String status;
    private String description;
    private String clinicName;
    private String doctorName;
    private String ageGroup;
}
