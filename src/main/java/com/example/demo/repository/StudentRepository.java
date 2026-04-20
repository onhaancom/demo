package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByIsActiveTrue();
    List<Student> findByFullNameContainingIgnoreCase(String name);
    boolean existsByCode(String code);
    boolean existsByEmail(String email);
    List<Student> findByClassCode(String classCode);
    List<Student> findByMajor(String major);
    List<Student> findByStatus(String status);
    List<Student> findByEnrollmentYear(Integer year);
}
