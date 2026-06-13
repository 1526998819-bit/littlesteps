package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.service.OssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final OssService ossService;

    /**
     * 单文件上传
     */
    @PostMapping
    public ApiResponse<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = ossService.upload(file);
            return ApiResponse.ok("上传成功", Map.of("url", url));
        } catch (Exception e) {
            return ApiResponse.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 多文件上传
     */
    @PostMapping("/batch")
    public ApiResponse<List<String>> uploadBatch(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    urls.add(ossService.upload(file));
                }
            }
            return ApiResponse.ok("上传成功", urls);
        } catch (Exception e) {
            return ApiResponse.error("批量上传失败: " + e.getMessage());
        }
    }
}
