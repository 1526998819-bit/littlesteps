package com.littlesteps.repository;

import com.littlesteps.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByChildIdOrderByEntryDateDesc(Long childId);

    List<JournalEntry> findByChildIdAndCategoryOrderByEntryDateDesc(Long childId, String category);
}
