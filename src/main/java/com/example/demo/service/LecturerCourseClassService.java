package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.LecturerCourseClass;
import com.example.demo.repository.LecturerCourseClassRepository;

@Service
public class LecturerCourseClassService {

    @Autowired
    private LecturerCourseClassRepository lecturerCourseClassRepository;

    public List<LecturerCourseClass> getAll() {
        return lecturerCourseClassRepository.findAll();
    }

    public List<LecturerCourseClass> getAllActive() {
        return lecturerCourseClassRepository.findByIsActiveTrue();
    }

    public LecturerCourseClass getById(UUID id) {
        return lecturerCourseClassRepository.findById(id).orElse(null);
    }

    public List<LecturerCourseClass> getByCourseClassId(UUID courseClassId) {
        return lecturerCourseClassRepository.findByCourseClassId(courseClassId);
    }

    public List<LecturerCourseClass> getByEmployeeId(UUID employeeId) {
        return lecturerCourseClassRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    public LecturerCourseClass create(LecturerCourseClass lecturerCourseClass) {
        lecturerCourseClass.setCreatedAt(LocalDateTime.now());
        lecturerCourseClass.setIsActive(true);
        return lecturerCourseClassRepository.save(lecturerCourseClass);
    }

    @Transactional
    public LecturerCourseClass update(UUID id, LecturerCourseClass lecturerCourseClass) {
        LecturerCourseClass existing = lecturerCourseClassRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setEmployeeId(lecturerCourseClass.getEmployeeId());
            existing.setCourseClassId(lecturerCourseClass.getCourseClassId());
            existing.setRole(lecturerCourseClass.getRole());
            existing.setUpdatedAt(LocalDateTime.now());
            return lecturerCourseClassRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        LecturerCourseClass lecturerCourseClass = lecturerCourseClassRepository.findById(id).orElse(null);
        if (lecturerCourseClass != null) {
            lecturerCourseClass.setDeletedAt(LocalDateTime.now());
            lecturerCourseClass.setIsActive(false);
            lecturerCourseClassRepository.save(lecturerCourseClass);
        }
    }
}