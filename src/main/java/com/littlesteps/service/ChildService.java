package com.littlesteps.service;

import com.littlesteps.model.Child;
import com.littlesteps.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final OssService ossService;

    public List<Child> findAll() {
        return childRepository.findAll();
    }

    public Child findById(Long id) {
        return childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Child not found with id: " + id));
    }

    public Child create(Child child) {
        return childRepository.save(child);
    }

    public Child update(Long id, Child child) {
        Child existing = findById(id);
        existing.setName(child.getName());
        existing.setGender(child.getGender());
        existing.setBirthDate(child.getBirthDate());
        existing.setAvatarUrl(child.getAvatarUrl());
        return childRepository.save(existing);
    }

    public Child updateAvatar(Long id, MultipartFile file) {
        Child child = findById(id);
        try {
            String url = ossService.upload(file);
            child.setAvatarUrl(url);
        } catch (IOException e) {
            throw new RuntimeException("头像上传失败: " + e.getMessage());
        }
        return childRepository.save(child);
    }

    public void delete(Long id) {
        childRepository.deleteById(id);
    }
}
