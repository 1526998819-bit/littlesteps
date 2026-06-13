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
public class JournalRequest {

    private Long childId;
    private LocalDate entryDate;
    private String title;
    private String content;
    private String mood;
    private String category;
}
