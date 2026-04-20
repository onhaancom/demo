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

import com.example.demo.model.SchoolYear;
import com.example.demo.service.SchoolYearService;

@RestController
@RequestMapping("/api/school-years")
@CrossOrigin(origins = "*")
public class SchoolYearApiController {

    @Autowired
    private SchoolYearService schoolYearService;

    @GetMapping
    public ResponseEntity<List<SchoolYear>> getAll() {
        return ResponseEntity.ok(schoolYearService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolYear> getById(@PathVariable UUID id) {
        SchoolYear schoolYear = schoolYearService.getById(id);
        if (schoolYear != null) {
            return ResponseEntity.ok(schoolYear);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SchoolYear> create(@RequestBody SchoolYear schoolYear) {
        SchoolYear created = schoolYearService.create(schoolYear);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolYear> update(@PathVariable UUID id, @RequestBody SchoolYear schoolYear) {
        SchoolYear updated = schoolYearService.update(id, schoolYear);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        schoolYearService.delete(id);
        return ResponseEntity.ok().build();
    }
}