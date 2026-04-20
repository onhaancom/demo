package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SchoolYear;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, UUID> {
    List<SchoolYear> findByIsActiveTrue();
    List<SchoolYear> findByCodeContainingIgnoreCase(String code);
    boolean existsByCode(String code);
    List<SchoolYear> findByIsCurrentTrue();
}