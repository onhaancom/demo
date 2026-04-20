package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, UUID> {
    List<Semester> findByIsActiveTrue();
    List<Semester> findBySchoolYearId(UUID schoolYearId);
    List<Semester> findByCodeContainingIgnoreCase(String code);
    boolean existsByCode(String code);
    List<Semester> findByIsCurrentTrue();
}