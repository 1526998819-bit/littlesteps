package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.dto.VaccineRequest;
import com.littlesteps.model.VaccineRecord;
import com.littlesteps.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    @GetMapping
    public ApiResponse<List<VaccineRecord>> list(
            @RequestParam Long childId,
            @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            return ApiResponse.ok(vaccineService.findByChildIdAndStatus(childId, status));
        }
        return ApiResponse.ok(vaccineService.findByChildId(childId));
    }

    @GetMapping("/pending")
    public ApiResponse<List<VaccineRecord>> pending(@RequestParam Long childId) {
        return ApiResponse.ok(vaccineService.findPending(childId));
    }

    @PostMapping
    public ApiResponse<VaccineRecord> create(@RequestBody VaccineRequest request) {
        return ApiResponse.ok("添加成功", vaccineService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<VaccineRecord> update(@PathVariable Long id, @RequestBody VaccineRequest request) {
        return ApiResponse.ok("更新成功", vaccineService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        vaccineService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }
}
