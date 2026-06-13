package com.littlesteps.service;

import com.littlesteps.model.GalleryPhoto;
import com.littlesteps.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final OssService ossService;

    public List<GalleryPhoto> findByChildId(Long childId) {
        return galleryRepository.findByChildIdOrderByTakenAtDesc(childId);
    }

    public List<GalleryPhoto> findTop5ByChildId(Long childId) {
        return galleryRepository.findTop5ByChildIdOrderByTakenAtDesc(childId);
    }

    public GalleryPhoto create(GalleryPhoto photo) {
        return galleryRepository.save(photo);
    }

    public void delete(Long id) {
        GalleryPhoto photo = galleryRepository.findById(id).orElse(null);
        if (photo != null) {
            ossService.delete(photo.getUrl());
            galleryRepository.deleteById(id);
        }
    }
}
