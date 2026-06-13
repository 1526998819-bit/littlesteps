package com.littlesteps.repository;

import com.littlesteps.model.GalleryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryPhoto, Long> {

    List<GalleryPhoto> findByChildIdOrderByTakenAtDesc(Long childId);

    List<GalleryPhoto> findTop5ByChildIdOrderByTakenAtDesc(Long childId);
}
