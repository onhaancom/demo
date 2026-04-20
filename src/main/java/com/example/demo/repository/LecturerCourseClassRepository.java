package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.LecturerCourseClass;

public interface LecturerCourseClassRepository extends JpaRepository<LecturerCourseClass, UUID> {
    List<LecturerCourseClass> findByIsActiveTrue();
    List<LecturerCourseClass> findByCourseClassId(UUID courseClassId);
    List<LecturerCourseClass> findByEmployeeId(UUID employeeId);
}