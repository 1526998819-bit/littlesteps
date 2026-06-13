package com.littlesteps.service;

import com.littlesteps.dto.GrowthRequest;
import com.littlesteps.model.GrowthRecord;
import com.littlesteps.repository.GrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GrowthService {

    private final GrowthRepository growthRepository;

    public List<GrowthRecord> findByChildId(Long childId) {
        return growthRepository.findByChildIdOrderByRecordDateAsc(childId);
    }

    public GrowthRecord create(GrowthRequest request) {
        GrowthRecord record = GrowthRecord.builder()
                .childId(request.getChildId())
                .recordDate(request.getRecordDate())
                .height(request.getHeight())
                .weight(request.getWeight())
                .headCircumference(request.getHeadCircumference())
                .notes(request.getNotes())
                .build();
        return growthRepository.save(record);
    }

    public void delete(Long id) {
        growthRepository.deleteById(id);
    }
}
