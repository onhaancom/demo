package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AcademicYear;
import com.example.demo.model.Semester;
import com.example.demo.repository.AcademicYearRepository;
import com.example.demo.repository.SemesterRepository;

@Service
public class AcademicYearService {

    @Autowired
    private AcademicYearRepository academicYearRepository;
    
    @Autowired
    private SemesterRepository semesterRepository;

    public List<AcademicYear> getAll() {
        List<AcademicYear> list = academicYearRepository.findByIsActiveTrue();
        return enrichWithSemesterInfo(list);
    }

    public List<AcademicYear> getAllActive() {
        List<AcademicYear> list = academicYearRepository.findByIsActiveTrue();
        return enrichWithSemesterInfo(list);
    }

    public AcademicYear getById(UUID id) {
        AcademicYear ay = academicYearRepository.findById(id).orElse(null);
        if (ay != null) {
            return enrichWithSemesterInfo(ay);
        }
        return null;
    }
    
    private AcademicYear enrichWithSemesterInfo(AcademicYear ay) {
        if (ay.getId() != null) {
            var semesters = semesterRepository.findBySchoolYearId(ay.getId());
            ay.setSemesterCount(semesters.size());
            ay.setSemesterNames(semesters.stream().map(Semester::getName).toList());
        }
        return ay;
    }
    
    private List<AcademicYear> enrichWithSemesterInfo(List<AcademicYear> list) {
        for (AcademicYear ay : list) {
            enrichWithSemesterInfo(ay);
        }
        return list;
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