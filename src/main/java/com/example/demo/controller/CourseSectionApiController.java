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

import com.example.demo.model.CourseSection;
import com.example.demo.service.CourseSectionService;

@RestController
@RequestMapping("/api/course-sections")
@CrossOrigin(origins = "*")
public class CourseSectionApiController {

    @Autowired
    private CourseSectionService courseSectionService;

    @GetMapping
    public ResponseEntity<List<CourseSection>> getAll() {
        return ResponseEntity.ok(courseSectionService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseSection> getById(@PathVariable UUID id) {
        CourseSection courseSection = courseSectionService.getById(id);
        if (courseSection != null) {
            return ResponseEntity.ok(courseSection);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<CourseSection>> getBySemesterId(@PathVariable UUID semesterId) {
        return ResponseEntity.ok(courseSectionService.getBySemesterId(semesterId));
    }

    @PostMapping
    public ResponseEntity<CourseSection> create(@RequestBody CourseSection courseSection) {
        CourseSection created = courseSectionService.create(courseSection);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseSection> update(@PathVariable UUID id, @RequestBody CourseSection courseSection) {
        CourseSection updated = courseSectionService.update(id, courseSection);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        courseSectionService.delete(id);
        return ResponseEntity.ok().build();
    }
}