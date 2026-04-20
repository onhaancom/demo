package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CourseSection;
import com.example.demo.repository.CourseSectionRepository;

@Service
public class CourseSectionService {

    @Autowired
    private CourseSectionRepository courseSectionRepository;

    public List<CourseSection> getAll() {
        return courseSectionRepository.findAll();
    }

    public List<CourseSection> getAllActive() {
        return courseSectionRepository.findByIsActiveTrue();
    }

    public CourseSection getById(UUID id) {
        return courseSectionRepository.findById(id).orElse(null);
    }

    public List<CourseSection> getBySemesterId(UUID semesterId) {
        return courseSectionRepository.findBySemesterId(semesterId);
    }

    @Transactional
    public CourseSection create(CourseSection courseSection) {
        courseSection.setCreatedAt(LocalDateTime.now());
        courseSection.setIsActive(true);
        return courseSectionRepository.save(courseSection);
    }

    @Transactional
    public CourseSection update(UUID id, CourseSection courseSection) {
        CourseSection existing = courseSectionRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(courseSection.getCode());
            existing.setName(courseSection.getName());
            existing.setCourseId(courseSection.getCourseId());
            existing.setSemesterId(courseSection.getSemesterId());
            existing.setRoomId(courseSection.getRoomId());
            existing.setBuildingId(courseSection.getBuildingId());
            existing.setMaxStudents(courseSection.getMaxStudents());
            existing.setMinStudents(courseSection.getMinStudents());
            existing.setClassType(courseSection.getClassType());
            existing.setStatus(courseSection.getStatus());
            existing.setRegistrationStart(courseSection.getRegistrationStart());
            existing.setRegistrationEnd(courseSection.getRegistrationEnd());
            existing.setNote(courseSection.getNote());
            existing.setUpdatedAt(LocalDateTime.now());
            return courseSectionRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        CourseSection courseSection = courseSectionRepository.findById(id).orElse(null);
        if (courseSection != null) {
            courseSection.setDeletedAt(LocalDateTime.now());
            courseSection.setIsActive(false);
            courseSectionRepository.save(courseSection);
        }
    }
}