package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Intake;

@Repository
public interface IntakeRepository extends JpaRepository<Intake, UUID> {
    List<Intake> findByIsActiveTrue();
    List<Intake> findByCodeContainingIgnoreCase(String code);
}
