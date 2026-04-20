package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.CourseSection;

public interface CourseSectionRepository extends JpaRepository<CourseSection, UUID> {
    List<CourseSection> findByIsActiveTrue();
    List<CourseSection> findBySemesterId(UUID semesterId);
    List<CourseSection> findByCodeContainingIgnoreCase(String code);
    
    @Query("SELECT cs FROM CourseSection cs WHERE cs.isActive = true AND cs.semesterId = :semesterId")
    List<CourseSection> findActiveBySemesterId(@Param("semesterId") UUID semesterId);
}