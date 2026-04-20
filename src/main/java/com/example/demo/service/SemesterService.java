package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Semester;
import com.example.demo.repository.SemesterRepository;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    public List<Semester> getAll() {
        return semesterRepository.findByIsActiveTrue();
    }

    public List<Semester> getAllActive() {
        return semesterRepository.findByIsActiveTrue();
    }

    public Semester getById(UUID id) {
        return semesterRepository.findById(id).orElse(null);
    }

    public List<Semester> getBySchoolYearId(UUID schoolYearId) {
        return semesterRepository.findBySchoolYearId(schoolYearId);
    }

    @Transactional
    public Semester create(Semester semester) {
        semester.setCreatedAt(LocalDateTime.now());
        semester.setIsActive(true);
        return semesterRepository.save(semester);
    }

    @Transactional
    public Semester update(UUID id, Semester semester) {
        Semester existing = semesterRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(semester.getCode());
            existing.setName(semester.getName());
            existing.setSemesterOrder(semester.getSemesterOrder());
            existing.setSchoolYearId(semester.getSchoolYearId());
            existing.setStartDate(semester.getStartDate());
            existing.setEndDate(semester.getEndDate());
            existing.setRegistrationStart(semester.getRegistrationStart());
            existing.setRegistrationEnd(semester.getRegistrationEnd());
            existing.setDescription(semester.getDescription());
            existing.setIsCurrent(semester.getIsCurrent());
            existing.setUpdatedAt(LocalDateTime.now());
            return semesterRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        Semester semester = semesterRepository.findById(id).orElse(null);
        if (semester != null) {
            semester.setIsActive(false);
            semesterRepository.save(semester);
        }
    }
}