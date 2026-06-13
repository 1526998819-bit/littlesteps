package com.littlesteps.service;

import com.littlesteps.dto.JournalRequest;
import com.littlesteps.model.JournalEntry;
import com.littlesteps.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;

    public List<JournalEntry> findByChildId(Long childId) {
        return journalRepository.findByChildIdOrderByEntryDateDesc(childId);
    }

    public List<JournalEntry> findByChildIdAndCategory(Long childId, String category) {
        return journalRepository.findByChildIdAndCategoryOrderByEntryDateDesc(childId, category);
    }

    public JournalEntry create(JournalRequest request) {
        JournalEntry entry = JournalEntry.builder()
                .childId(request.getChildId())
                .entryDate(request.getEntryDate())
                .title(request.getTitle())
                .content(request.getContent())
                .mood(request.getMood())
                .category(request.getCategory())
                .build();
        return journalRepository.save(entry);
    }

    public void delete(Long id) {
        journalRepository.deleteById(id);
    }
}
