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

import com.example.demo.model.Semester;
import com.example.demo.service.SemesterService;

@RestController
@RequestMapping("/api/semesters")
@CrossOrigin(origins = "*")
public class SemesterApiController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping
    public ResponseEntity<List<Semester>> getAll() {
        return ResponseEntity.ok(semesterService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semester> getById(@PathVariable UUID id) {
        Semester semester = semesterService.getById(id);
        if (semester != null) {
            return ResponseEntity.ok(semester);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/school-year/{schoolYearId}")
    public ResponseEntity<List<Semester>> getBySchoolYearId(@PathVariable UUID schoolYearId) {
        return ResponseEntity.ok(semesterService.getBySchoolYearId(schoolYearId));
    }

    @PostMapping
    public ResponseEntity<Semester> create(@RequestBody Semester semester) {
        Semester created = semesterService.create(semester);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semester> update(@PathVariable UUID id, @RequestBody Semester semester) {
        Semester updated = semesterService.update(id, semester);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        semesterService.delete(id);
        return ResponseEntity.ok().build();
    }
}