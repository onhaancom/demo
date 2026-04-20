package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "lecturer_course_classes")
public class LecturerCourseClass {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "UNIQUEIDENTIFIER", updatable = false, nullable = false)
    private UUID id;

    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID employeeId;

    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID courseSectionId;

    @Column(length = 50)
    private String role = "LECTURER";

    private Integer teachingHours;

    private Boolean isPrimary = false;

    @Column(length = 500)
    private String note;

    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID createdBy;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID updatedBy;

    // ===== Transient fields for display =====
    @Transient
    private String employeeName;
    
    @Transient
    private String employeeCode;
    
    @Transient
    private String courseSectionCode;
    
    @Transient
    private String courseName;

    // ===== Constructor =====
    public LecturerCourseClass() {}

    // ===== Getters and Setters =====
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }

    public UUID getCourseSectionId() { return courseSectionId; }
    public void setCourseSectionId(UUID courseSectionId) { this.courseSectionId = courseSectionId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getTeachingHours() { return teachingHours; }
    public void setTeachingHours(Integer teachingHours) { this.teachingHours = teachingHours; }

    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }

    // ===== Transient Getters and Setters =====
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getCourseSectionCode() { return courseSectionCode; }
    public void setCourseSectionCode(String courseSectionCode) { this.courseSectionCode = courseSectionCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
}