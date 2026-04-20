package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.SchoolYear;
import com.example.demo.repository.SchoolYearRepository;

@Service
public class SchoolYearService {

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    public List<SchoolYear> getAll() {
        return schoolYearRepository.findByIsActiveTrue();
    }

    public List<SchoolYear> getAllActive() {
        return schoolYearRepository.findByIsActiveTrue();
    }

    public SchoolYear getById(UUID id) {
        return schoolYearRepository.findById(id).orElse(null);
    }

    @Transactional
    public SchoolYear create(SchoolYear schoolYear) {
        schoolYear.setCreatedAt(LocalDateTime.now());
        schoolYear.setIsActive(true);
        return schoolYearRepository.save(schoolYear);
    }

    @Transactional
    public SchoolYear update(UUID id, SchoolYear schoolYear) {
        SchoolYear existing = schoolYearRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(schoolYear.getCode());
            existing.setName(schoolYear.getName());
            existing.setStartYear(schoolYear.getStartYear());
            existing.setEndYear(schoolYear.getEndYear());
            existing.setDescription(schoolYear.getDescription());
            existing.setStartDate(schoolYear.getStartDate());
            existing.setEndDate(schoolYear.getEndDate());
            existing.setIsCurrent(schoolYear.getIsCurrent());
            existing.setUpdatedAt(LocalDateTime.now());
            return schoolYearRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        SchoolYear schoolYear = schoolYearRepository.findById(id).orElse(null);
        if (schoolYear != null) {
            schoolYear.setIsActive(false);
            schoolYearRepository.save(schoolYear);
        }
    }
}