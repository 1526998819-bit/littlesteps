package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.dto.JournalRequest;
import com.littlesteps.model.JournalEntry;
import com.littlesteps.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @GetMapping
    public ApiResponse<List<JournalEntry>> list(
            @RequestParam Long childId,
            @RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return ApiResponse.ok(journalService.findByChildIdAndCategory(childId, category));
        }
        return ApiResponse.ok(journalService.findByChildId(childId));
    }

    @PostMapping
    public ApiResponse<JournalEntry> create(@RequestBody JournalRequest request) {
        return ApiResponse.ok("记录成功", journalService.create(request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        journalService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }
}
