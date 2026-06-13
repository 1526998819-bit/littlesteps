package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.model.GalleryPhoto;
import com.littlesteps.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public ApiResponse<List<GalleryPhoto>> list(@RequestParam Long childId) {
        return ApiResponse.ok(galleryService.findByChildId(childId));
    }

    @GetMapping("/recent")
    public ApiResponse<List<GalleryPhoto>> recent(@RequestParam Long childId) {
        return ApiResponse.ok(galleryService.findTop5ByChildId(childId));
    }

    @PostMapping
    public ApiResponse<GalleryPhoto> create(@RequestBody GalleryPhoto photo) {
        return ApiResponse.ok("上传成功", galleryService.create(photo));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        galleryService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }
}
