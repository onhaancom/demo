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

import com.example.demo.model.AcademicYear;
import com.example.demo.service.AcademicYearService;

@RestController
@RequestMapping("/api/academic-years")
@CrossOrigin(origins = "*")
public class AcademicYearApiController {

    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping
    public ResponseEntity<List<AcademicYear>> getAll() {
        return ResponseEntity.ok(academicYearService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicYear> getById(@PathVariable UUID id) {
        AcademicYear academicYear = academicYearService.getById(id);
        if (academicYear != null) {
            return ResponseEntity.ok(academicYear);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AcademicYear> create(@RequestBody AcademicYear academicYear) {
        AcademicYear created = academicYearService.create(academicYear);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYear> update(@PathVariable UUID id, @RequestBody AcademicYear academicYear) {
        AcademicYear updated = academicYearService.update(id, academicYear);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        academicYearService.delete(id);
        return ResponseEntity.ok().build();
    }
}