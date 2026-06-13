package com.littlesteps.service;

import com.littlesteps.dto.VaccineRequest;
import com.littlesteps.model.VaccineRecord;
import com.littlesteps.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;

    public List<VaccineRecord> findByChildId(Long childId) {
        return vaccineRepository.findByChildIdOrderByScheduledDateAsc(childId);
    }

    public List<VaccineRecord> findByChildIdAndStatus(Long childId, String status) {
        return vaccineRepository.findByChildIdAndStatusOrderByScheduledDateAsc(childId, status);
    }

    public List<VaccineRecord> findPending(Long childId) {
        return vaccineRepository.findPendingVaccines(childId);
    }

    public VaccineRecord create(VaccineRequest request) {
        VaccineRecord record = VaccineRecord.builder()
                .childId(request.getChildId())
                .name(request.getName())
                .dose(request.getDose())
                .scheduledDate(request.getScheduledDate())
                .administeredDate(request.getAdministeredDate())
                .status(request.getStatus() != null ? request.getStatus() : "Pending")
                .description(request.getDescription())
                .clinicName(request.getClinicName())
                .doctorName(request.getDoctorName())
                .ageGroup(request.getAgeGroup())
                .build();
        return vaccineRepository.save(record);
    }

    public VaccineRecord update(Long id, VaccineRequest request) {
        VaccineRecord existing = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine record not found with id: " + id));
        existing.setName(request.getName());
        existing.setDose(request.getDose());
        existing.setScheduledDate(request.getScheduledDate());
        existing.setAdministeredDate(request.getAdministeredDate());
        existing.setStatus(request.getStatus());
        existing.setDescription(request.getDescription());
        existing.setClinicName(request.getClinicName());
        existing.setDoctorName(request.getDoctorName());
        existing.setAgeGroup(request.getAgeGroup());
        return vaccineRepository.save(existing);
    }

    public void delete(Long id) {
        vaccineRepository.deleteById(id);
    }

    public long countCompleted(Long childId) {
        return vaccineRepository.countByChildIdAndStatus(childId, "Completed");
    }
}
