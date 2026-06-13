package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.model.Child;
import com.littlesteps.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @GetMapping
    public ApiResponse<List<Child>> list() {
        return ApiResponse.ok(childService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Child> get(@PathVariable Long id) {
        return ApiResponse.ok(childService.findById(id));
    }

    @PostMapping
    public ApiResponse<Child> create(@RequestBody Child child) {
        return ApiResponse.ok("创建成功", childService.create(child));
    }

    @PutMapping("/{id}")
    public ApiResponse<Child> update(@PathVariable Long id, @RequestBody Child child) {
        return ApiResponse.ok("更新成功", childService.update(id, child));
    }

    @PostMapping("/{id}/avatar")
    public ApiResponse<Child> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ApiResponse.ok("头像更新成功", childService.updateAvatar(id, file));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        childService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }
}
