package com.example.demo.model;

import java.math.BigDecimal;
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
@Table(name = "student_course_sections")
public class StudentCourseSection {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "UNIQUEIDENTIFIER", updatable = false, nullable = false)
    private UUID id;

    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID studentId;

    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID courseSectionId;

    @Column(length = 50)
    private String status = "PENDING";

    private LocalDateTime registrationDate;

    private LocalDateTime approvedDate;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID approvedBy;

    @Column(length = 10)
    private String grade;

    @Column(precision = 5, scale = 2)
    private BigDecimal gradeScore;

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
    private String studentName;
    
    @Transient
    private String studentCode;
    
    @Transient
    private String courseSectionCode;
    
    @Transient
    private String courseName;
    
    @Transient
    private String semesterName;
    
    @Transient
    private String academicYearName;

    // ===== Constructor =====
    public StudentCourseSection() {}

    // ===== Getters and Setters =====
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public UUID getCourseSectionId() { return courseSectionId; }
    public void setCourseSectionId(UUID courseSectionId) { this.courseSectionId = courseSectionId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public LocalDateTime getApprovedDate() { return approvedDate; }
    public void setApprovedDate(LocalDateTime approvedDate) { this.approvedDate = approvedDate; }

    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public BigDecimal getGradeScore() { return gradeScore; }
    public void setGradeScore(BigDecimal gradeScore) { this.gradeScore = gradeScore; }

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
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getCourseSectionCode() { return courseSectionCode; }
    public void setCourseSectionCode(String courseSectionCode) { this.courseSectionCode = courseSectionCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    
    public String getAcademicYearName() { return academicYearName; }
    public void setAcademicYearName(String academicYearName) { this.academicYearName = academicYearName; }
}