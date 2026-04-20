package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.StudentCourseSection;

@Repository
public interface StudentCourseSectionRepository extends JpaRepository<StudentCourseSection, UUID> {
    List<StudentCourseSection> findByIsActiveTrue();
    List<StudentCourseSection> findByCourseSectionId(UUID courseSectionId);
    List<StudentCourseSection> findByStudentId(UUID studentId);
    List<StudentCourseSection> findByStatus(String status);
    
    @Query("SELECT COUNT(scs) FROM StudentCourseSection scs WHERE scs.courseSectionId = :courseSectionId AND scs.isActive = true")
    Long countByCourseSectionId(@Param("courseSectionId") UUID courseSectionId);
    
    @Query("SELECT COUNT(scs) FROM StudentCourseSection scs WHERE scs.courseSectionId = :courseSectionId AND scs.status = :status AND scs.isActive = true")
    Long countByCourseSectionIdAndStatus(@Param("courseSectionId") UUID courseSectionId, @Param("status") String status);
    
    boolean existsByStudentIdAndCourseSectionId(UUID studentId, UUID courseSectionId);
}