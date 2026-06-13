package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.dto.GrowthRequest;
import com.littlesteps.model.GrowthRecord;
import com.littlesteps.service.GrowthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/growth")
@RequiredArgsConstructor
public class GrowthController {

    private final GrowthService growthService;

    @GetMapping
    public ApiResponse<List<GrowthRecord>> list(@RequestParam Long childId) {
        return ApiResponse.ok(growthService.findByChildId(childId));
    }

    @PostMapping
    public ApiResponse<GrowthRecord> create(@RequestBody GrowthRequest request) {
        return ApiResponse.ok("记录成功", growthService.create(request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        growthService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }
}
