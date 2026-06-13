package com.littlesteps.repository;

import com.littlesteps.model.VaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<VaccineRecord, Long> {

    List<VaccineRecord> findByChildIdOrderByScheduledDateAsc(Long childId);

    List<VaccineRecord> findByChildIdAndStatusOrderByScheduledDateAsc(Long childId, String status);

    @Query("SELECT v FROM VaccineRecord v WHERE v.childId = :childId AND v.status IN ('Pending', 'Overdue') ORDER BY v.scheduledDate ASC")
    List<VaccineRecord> findPendingVaccines(@Param("childId") Long childId);

    long countByChildIdAndStatus(Long childId, String status);
}
