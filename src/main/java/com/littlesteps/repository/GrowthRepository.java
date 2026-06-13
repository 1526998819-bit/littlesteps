package com.littlesteps.repository;

import com.littlesteps.model.GrowthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GrowthRepository extends JpaRepository<GrowthRecord, Long> {

    List<GrowthRecord> findByChildIdOrderByRecordDateAsc(Long childId);

    List<GrowthRecord> findByChildIdOrderByRecordDateDesc(Long childId);
}
