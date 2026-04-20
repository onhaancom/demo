package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AcademicYear;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, UUID> {
    List<AcademicYear> findByIsActiveTrue();
    List<AcademicYear> findByCodeContainingIgnoreCase(String code);
    boolean existsByCode(String code);
    List<AcademicYear> findByIsCurrentTrue();
}