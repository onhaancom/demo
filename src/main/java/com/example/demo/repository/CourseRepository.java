package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByIsActiveTrue();
    boolean existsByCode(String code);
    List<Course> findByDepartment(String department);
}