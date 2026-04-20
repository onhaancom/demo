package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findByIsActiveTrue();
    }

    public Student getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Student create(Student student) {
        if (student.getIsActive() == null) {
            student.setIsActive(true);
        }
        return repo.save(student);
    }

    public Student update(UUID id, Student student) {
        Student old = getById(id);
        if (old == null) return null;

        old.setCode(student.getCode());
        old.setFirstName(student.getFirstName());
        old.setLastName(student.getLastName());
        old.setGender(student.getGender());
        old.setDateOfBirth(student.getDateOfBirth());
        old.setEmail(student.getEmail());
        old.setPhone(student.getPhone());
        old.setAddress(student.getAddress());
        old.setCccd(student.getCccd());
        old.setClassCode(student.getClassCode());
        old.setMajor(student.getMajor());
        old.setEnrollmentYear(student.getEnrollmentYear());
        old.setStatus(student.getStatus());

        return repo.save(old);
    }

    public void delete(UUID id) {
        Student student = getById(id);
        if (student != null) {
            student.setIsActive(false);
            repo.save(student);
        }
    }

    public List<Student> search(String name) {
        return repo.findByFullNameContainingIgnoreCase(name);
    }
}