package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AcademicYear;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.repository.SemesterRepository;

@Service
public class AcademicYearService {

    @Autowired
    private AcademicYearRepository academicYearRepository;
    
    @Autowired
    private SemesterRepository semesterRepository;

    public List<AcademicYear> getAll() {
        return academicYearRepository.findByIsActiveTrue();
    }

    public List<AcademicYear> getAllActive() {
        return academicYearRepository.findByIsActiveTrue();
    }

    public AcademicYear getById(UUID id) {
        return academicYearRepository.findById(id).orElse(null);
    }

    @Transactional
    public AcademicYear create(AcademicYear academicYear) {
        academicYear.setCreatedAt(LocalDateTime.now());
        academicYear.setIsActive(true);
        return academicYearRepository.save(academicYear);
    }

    @Transactional
    public AcademicYear update(UUID id, AcademicYear academicYear) {
        AcademicYear existing = academicYearRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(academicYear.getCode());
            existing.setName(academicYear.getName());
            existing.setYear(academicYear.getYear());
            existing.setStartYear(academicYear.getStartYear());
            existing.setEndYear(academicYear.getEndYear());
            existing.setDescription(academicYear.getDescription());
            existing.setStartDate(academicYear.getStartDate());
            existing.setEndDate(academicYear.getEndDate());
            existing.setIsCurrent(academicYear.getIsCurrent());
            existing.setUpdatedAt(LocalDateTime.now());
            return academicYearRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        AcademicYear academicYear = academicYearRepository.findById(id).orElse(null);
        if (academicYear != null) {
            academicYear.setIsActive(false);
            academicYearRepository.save(academicYear);
        }
    }
}