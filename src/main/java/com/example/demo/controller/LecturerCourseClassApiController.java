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

import com.example.demo.model.LecturerCourseClass;
import com.example.demo.service.LecturerCourseClassService;

@RestController
@RequestMapping("/api/lecturer-course-classes")
@CrossOrigin(origins = "*")
public class LecturerCourseClassApiController {

    @Autowired
    private LecturerCourseClassService lecturerCourseClassService;

    @GetMapping
    public ResponseEntity<List<LecturerCourseClass>> getAll() {
        return ResponseEntity.ok(lecturerCourseClassService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerCourseClass> getById(@PathVariable UUID id) {
        LecturerCourseClass lecturerCourseClass = lecturerCourseClassService.getById(id);
        if (lecturerCourseClass != null) {
            return ResponseEntity.ok(lecturerCourseClass);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/course-section/{courseSectionId}")
    public ResponseEntity<List<LecturerCourseClass>> getByCourseSectionId(@PathVariable UUID courseSectionId) {
        return ResponseEntity.ok(lecturerCourseClassService.getByCourseSectionId(courseSectionId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LecturerCourseClass>> getByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(lecturerCourseClassService.getByEmployeeId(employeeId));
    }

    @PostMapping
    public ResponseEntity<LecturerCourseClass> create(@RequestBody LecturerCourseClass lecturerCourseClass) {
        LecturerCourseClass created = lecturerCourseClassService.create(lecturerCourseClass);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturerCourseClass> update(@PathVariable UUID id, @RequestBody LecturerCourseClass lecturerCourseClass) {
        LecturerCourseClass updated = lecturerCourseClassService.update(id, lecturerCourseClass);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        lecturerCourseClassService.delete(id);
        return ResponseEntity.ok().build();
    }
}