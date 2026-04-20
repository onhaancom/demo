package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
    List<Building> findByIsActiveTrue();
    boolean existsByCode(String code);
}