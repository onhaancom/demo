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
@Table(name = "course_sections")
public class CourseSection {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "UNIQUEIDENTIFIER", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 50, nullable = false, unique = true)
    private String code;

    @Column(length = 255)
    private String name;

    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID courseId;

    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID semesterId;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID lecturerId;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID roomId;

    private Integer maxStudents = 50;
    private Integer minStudents = 5;
    private Integer currentStudents = 0;

    @Column(length = 50)
    private String classType;

    @Column(length = 50)
    private String status = "OPEN";

    @Column(length = 500)
    private String scheduleInfo;

    @Column(length = 500)
    private String note;

    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID createdBy;

    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID updatedBy;

    // ===== Transient fields for display (not stored in DB) =====
    @Transient
    private String courseName;
    @Transient
    private String semesterName;
    @Transient
    private String lecturerName;
    @Transient
    private String roomName;

    // ===== Constructor =====
    public CourseSection() {}

    // ===== Getters and Setters =====
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public UUID getSemesterId() { return semesterId; }
    public void setSemesterId(UUID semesterId) { this.semesterId = semesterId; }

    public UUID getLecturerId() { return lecturerId; }
    public void setLecturerId(UUID lecturerId) { this.lecturerId = lecturerId; }

    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }

    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }

    public Integer getMinStudents() { return minStudents; }
    public void setMinStudents(Integer minStudents) { this.minStudents = minStudents; }

    public Integer getCurrentStudents() { return currentStudents; }
    public void setCurrentStudents(Integer currentStudents) { this.currentStudents = currentStudents; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getScheduleInfo() { return scheduleInfo; }
    public void setScheduleInfo(String scheduleInfo) { this.scheduleInfo = scheduleInfo; }

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
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }

    public String getLecturerName() { return lecturerName; }
    public void setLecturerName(String lecturerName) { this.lecturerName = lecturerName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
}