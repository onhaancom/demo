package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findByIsActiveTrue();
    }

    public List<Employee> getAllActive() {
        return employeeRepository.findByIsActiveTrue();
    }

    public Employee getById(UUID id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public boolean existsByCode(String code) {
        return employeeRepository.existsByCode(code);
    }

    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Transactional
    public Employee create(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setIsActive(true);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(UUID id, Employee employee) {
        Employee existing = employeeRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(employee.getCode());
            existing.setEmployeeType(employee.getEmployeeType());
            existing.setFirstName(employee.getFirstName());
            existing.setLastName(employee.getLastName());
            existing.setGender(employee.getGender());
            existing.setDateOfBirth(employee.getDateOfBirth());
            existing.setEmail(employee.getEmail());
            existing.setPhone(employee.getPhone());
            existing.setAddress(employee.getAddress());
            existing.setDepartment(employee.getDepartment());
            existing.setPosition(employee.getPosition());
            existing.setDegree(employee.getDegree());
            existing.setHireDate(employee.getHireDate());
            existing.setUpdatedAt(LocalDateTime.now());
            return employeeRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setIsActive(false);
            employeeRepository.save(employee);
        }
    }
}