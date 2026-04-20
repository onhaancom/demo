package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.StudentCourseSection;
import com.example.demo.repository.StudentCourseSectionRepository;

@Service
public class StudentCourseSectionService {

    @Autowired
    private StudentCourseSectionRepository studentCourseSectionRepository;

    public List<StudentCourseSection> getAll() {
        return studentCourseSectionRepository.findAll();
    }

    public List<StudentCourseSection> getAllActive() {
        return studentCourseSectionRepository.findByIsActiveTrue();
    }

    public StudentCourseSection getById(UUID id) {
        return studentCourseSectionRepository.findById(id).orElse(null);
    }

    public List<StudentCourseSection> getByCourseSectionId(UUID courseSectionId) {
        return studentCourseSectionRepository.findByCourseSectionId(courseSectionId);
    }

    public List<StudentCourseSection> getByStudentId(UUID studentId) {
        return studentCourseSectionRepository.findByStudentId(studentId);
    }

    public Long countByCourseSectionId(UUID courseSectionId) {
        return studentCourseSectionRepository.countByCourseSectionId(courseSectionId);
    }

    @Transactional
    public StudentCourseSection create(StudentCourseSection studentCourseSection) {
        studentCourseSection.setCreatedAt(LocalDateTime.now());
        studentCourseSection.setRegisteredAt(LocalDateTime.now());
        studentCourseSection.setIsActive(true);
        return studentCourseSectionRepository.save(studentCourseSection);
    }

    @Transactional
    public StudentCourseSection update(UUID id, StudentCourseSection studentCourseSection) {
        StudentCourseSection existing = studentCourseSectionRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStudentId(studentCourseSection.getStudentId());
            existing.setCourseSectionId(studentCourseSection.getCourseSectionId());
            existing.setStatus(studentCourseSection.getStatus());
            existing.setNote(studentCourseSection.getNote());
            existing.setUpdatedAt(LocalDateTime.now());
            return studentCourseSectionRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        StudentCourseSection studentCourseSection = studentCourseSectionRepository.findById(id).orElse(null);
        if (studentCourseSection != null) {
            studentCourseSection.setDeletedAt(LocalDateTime.now());
            studentCourseSection.setIsActive(false);
            studentCourseSectionRepository.save(studentCourseSection);
        }
    }
}