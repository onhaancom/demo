package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Intake;
import com.example.demo.repository.IntakeRepository;

@Service
public class IntakeService {

    @Autowired
    private IntakeRepository intakeRepository;

    public List<Intake> getAll() {
        return intakeRepository.findAll();
    }

    public List<Intake> getAllActive() {
        return intakeRepository.findByIsActiveTrue();
    }

    public Intake getById(UUID id) {
        return intakeRepository.findById(id).orElse(null);
    }

    public Intake create(Intake intake) {
        intake.setCreatedAt(LocalDateTime.now());
        intake.setUpdatedAt(LocalDateTime.now());
        intake.setIsActive(true);
        return intakeRepository.save(intake);
    }

    public Intake update(UUID id, Intake intakeDetails) {
        Intake intake = getById(id);
        if (intake != null) {
            intake.setCode(intakeDetails.getCode());
            intake.setName(intakeDetails.getName());
            intake.setStartYear(intakeDetails.getStartYear());
            intake.setEndYear(intakeDetails.getEndYear());
            intake.setDescription(intakeDetails.getDescription());
            intake.setUpdatedAt(LocalDateTime.now());
            return intakeRepository.save(intake);
        }
        return null;
    }

    public void delete(UUID id) {
        intakeRepository.deleteById(id);
    }
}
