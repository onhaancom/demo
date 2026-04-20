package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByIsActiveTrue();
    boolean existsByCode(String code);
    boolean existsByEmail(String email);
    List<Employee> findByDepartment(String department);
    List<Employee> findByEmployeeType(String employeeType);
}