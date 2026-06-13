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
public class GrowthRequest {

    private Long childId;
    private LocalDate recordDate;
    private Double height;
    private Double weight;
    private Double headCircumference;
    private String notes;
}
