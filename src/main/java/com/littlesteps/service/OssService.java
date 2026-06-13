package com.littlesteps.service;

import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class OssService {

    @Autowired(required = false)
    private OSS ossClient;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    public String upload(MultipartFile file) throws IOException {
        if (ossClient == null) {
            throw new RuntimeException("OSS 未配置，请设置 ALIYUN_OSS_ACCESS_KEY_ID 环境变量");
        }
        String datePath = java.time.LocalDate.now().toString().replace("-", "/");
        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        String objectKey = "gallery/" + datePath + "/" + UUID.randomUUID() + ext;
        ossClient.putObject(bucket, objectKey, file.getInputStream());
        return "https://" + bucket + "." + endpoint + "/" + objectKey;
    }

    public void delete(String url) {
        if (ossClient == null) return;
        String prefix = "https://" + bucket + "." + endpoint + "/";
        if (url != null && url.startsWith(prefix)) {
            String objectKey = url.substring(prefix.length());
            ossClient.deleteObject(bucket, objectKey);
        }
    }
}
