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

import com.example.demo.model.Intake;
import com.example.demo.service.IntakeService;

@RestController
@RequestMapping("/api/intakes")
@CrossOrigin(origins = "*")
public class IntakeApiController {

    @Autowired
    private IntakeService intakeService;

    @GetMapping
    public ResponseEntity<List<Intake>> getAll() {
        return ResponseEntity.ok(intakeService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intake> getById(@PathVariable UUID id) {
        Intake intake = intakeService.getById(id);
        if (intake != null) {
            return ResponseEntity.ok(intake);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Intake> create(@RequestBody Intake intake) {
        Intake created = intakeService.create(intake);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intake> update(@PathVariable UUID id, @RequestBody Intake intake) {
        Intake updated = intakeService.update(id, intake);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        intakeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
