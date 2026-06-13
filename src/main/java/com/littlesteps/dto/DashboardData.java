package com.littlesteps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardData {

    private ChildSummary child;
    private VaccineSummary vaccines;
    private List<GrowthPoint> recentGrowth;
    private List<GalleryItem> recentPhotos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChildSummary {
        private Long id;
        private String name;
        private String gender;
        private int ageInMonths;
        private String birthDate;
        private String avatarUrl;
        private List<String> milestones;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VaccineSummary {
        private int completed;
        private int total;
        private String nextVaccine;
        private String nextDate;
        private String nextLocation;
        private String nextDoctor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GrowthPoint {
        private String month;
        private double value;
        private String unit;
        private String label;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GalleryItem {
        private Long id;
        private String url;
        private String caption;
        private String type;
        private String timeAgo;
    }
}
