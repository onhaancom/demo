package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.StudentCourseSection;
import com.example.demo.service.StudentCourseSectionService;

@RestController
@RequestMapping("/api/student-course-sections")
@CrossOrigin(origins = "*")
public class StudentCourseSectionApiController {

    @Autowired
    private StudentCourseSectionService studentCourseSectionService;

    @GetMapping
    public ResponseEntity<List<StudentCourseSection>> getAll() {
        return ResponseEntity.ok(studentCourseSectionService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseSection> getById(@PathVariable UUID id) {
        StudentCourseSection studentCourseSection = studentCourseSectionService.getById(id);
        if (studentCourseSection != null) {
            return ResponseEntity.ok(studentCourseSection);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/course-section/{courseSectionId}")
    public ResponseEntity<List<StudentCourseSection>> getByCourseSectionId(@PathVariable UUID courseSectionId) {
        return ResponseEntity.ok(studentCourseSectionService.getByCourseSectionId(courseSectionId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentCourseSection>> getByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(studentCourseSectionService.getByStudentId(studentId));
    }

    @GetMapping("/count/{courseSectionId}")
    public ResponseEntity<Long> countByCourseSectionId(@PathVariable UUID courseSectionId) {
        return ResponseEntity.ok(studentCourseSectionService.countByCourseSectionId(courseSectionId));
    }

    @PostMapping
    public ResponseEntity<StudentCourseSection> create(@RequestBody StudentCourseSection studentCourseSection) {
        StudentCourseSection created = studentCourseSectionService.create(studentCourseSection);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCourseSection> update(@PathVariable UUID id, @RequestBody StudentCourseSection studentCourseSection) {
        StudentCourseSection updated = studentCourseSectionService.update(id, studentCourseSection);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentCourseSectionService.delete(id);
        return ResponseEntity.ok().build();
    }
}