package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.dto.DashboardData;
import com.littlesteps.model.Child;
import com.littlesteps.model.GalleryPhoto;
import com.littlesteps.model.GrowthRecord;
import com.littlesteps.model.VaccineRecord;
import com.littlesteps.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ChildService childService;
    private final GrowthService growthService;
    private final VaccineService vaccineService;
    private final GalleryService galleryService;
    private final JournalService journalService;

    @GetMapping
    public ApiResponse<DashboardData> getDashboard(@RequestParam(defaultValue = "1") Long childId) {
        Child child = childService.findById(childId);
        List<GrowthRecord> growthRecords = growthService.findByChildId(childId);
        List<VaccineRecord> allVaccines = vaccineService.findByChildId(childId);
        List<GalleryPhoto> recentPhotos = galleryService.findTop5ByChildId(childId);

        DashboardData data = DashboardData.builder()
                .child(buildChildSummary(child))
                .vaccines(buildVaccineSummary(allVaccines))
                .recentGrowth(buildGrowthPoints(growthRecords))
                .recentPhotos(buildGalleryItems(recentPhotos))
                .build();

        return ApiResponse.ok(data);
    }

    private DashboardData.ChildSummary buildChildSummary(Child child) {
        int ageInMonths = Period.between(child.getBirthDate(), LocalDate.now()).getMonths()
                + Period.between(child.getBirthDate(), LocalDate.now()).getYears() * 12;

        List<String> milestones = new ArrayList<>();
        // These would be dynamic in production; using placeholders
        milestones.add("⭐ 健康成长中");
        if (ageInMonths >= 12) milestones.add("🚶 已会走路");
        if (ageInMonths >= 9) milestones.add("💬 会说简单词汇");

        return DashboardData.ChildSummary.builder()
                .id(child.getId())
                .name(child.getName())
                .gender(child.getGender())
                .ageInMonths(ageInMonths)
                .birthDate(child.getBirthDate().toString())
                .avatarUrl(child.getAvatarUrl())
                .milestones(milestones)
                .build();
    }

    private DashboardData.VaccineSummary buildVaccineSummary(List<VaccineRecord> vaccines) {
        long completed = vaccines.stream()
                .filter(v -> "Completed".equals(v.getStatus()))
                .count();
        int total = vaccines.size();

        VaccineRecord next = vaccines.stream()
                .filter(v -> "Pending".equals(v.getStatus()) || "Overdue".equals(v.getStatus()))
                .findFirst()
                .orElse(null);

        String nextName = next != null ? next.getName() : "全部完成";
        String nextDate = next != null && next.getScheduledDate() != null
                ? next.getScheduledDate().toString()
                : "待定";
        String location = next != null ? (next.getClinicName() != null ? next.getClinicName() : "社区卫生中心") : "";
        String doctor = next != null ? (next.getDoctorName() != null ? next.getDoctorName() : "") : "";

        return DashboardData.VaccineSummary.builder()
                .completed((int) completed)
                .total(total)
                .nextVaccine(nextName)
                .nextDate(nextDate)
                .nextLocation(location)
                .nextDoctor(doctor)
                .build();
    }

    private List<DashboardData.GrowthPoint> buildGrowthPoints(List<GrowthRecord> records) {
        if (records.isEmpty()) return List.of();

        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("M月");
        List<DashboardData.GrowthPoint> points = new ArrayList<>();

        for (GrowthRecord r : records) {
            String month = r.getRecordDate().format(monthFmt);
            if (r.getHeight() != null) {
                points.add(DashboardData.GrowthPoint.builder()
                        .month(month)
                        .value(r.getHeight())
                        .unit("cm")
                        .label("身高")
                        .build());
            }
            if (r.getWeight() != null) {
                points.add(DashboardData.GrowthPoint.builder()
                        .month(month)
                        .value(r.getWeight())
                        .unit("kg")
                        .label("体重")
                        .build());
            }
        }
        return points;
    }

    private List<DashboardData.GalleryItem> buildGalleryItems(List<GalleryPhoto> photos) {
        return photos.stream()
                .map(p -> {
                    String timeAgo;
                    long days = ChronoUnit.DAYS.between(p.getTakenAt().toLocalDate(), LocalDate.now());
                    if (days == 0) timeAgo = "今天";
                    else if (days == 1) timeAgo = "昨天";
                    else if (days < 7) timeAgo = days + "天前";
                    else if (days < 30) timeAgo = (days / 7) + "周前";
                    else timeAgo = (days / 30) + "个月前";

                    return DashboardData.GalleryItem.builder()
                            .id(p.getId())
                            .url(p.getUrl())
                            .caption(p.getCaption())
                            .type(p.getType())
                            .timeAgo(timeAgo)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
