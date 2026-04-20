package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LecturerCourseClass;

@Repository
public interface LecturerCourseClassRepository extends JpaRepository<LecturerCourseClass, UUID> {
    List<LecturerCourseClass> findByIsActiveTrue();
    List<LecturerCourseClass> findByCourseSectionId(UUID courseSectionId);
    List<LecturerCourseClass> findByEmployeeId(UUID employeeId);
    List<LecturerCourseClass> findByRole(String role);
    List<LecturerCourseClass> findByIsPrimaryTrue();
}