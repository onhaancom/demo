package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Course;
import com.example.demo.model.CourseSection;
import com.example.demo.model.Student;
import com.example.demo.model.StudentCourseSection;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseSectionRepository;
import com.example.demo.repository.StudentCourseSectionRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentCourseSectionService {

    @Autowired
    private StudentCourseSectionRepository studentCourseSectionRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseSectionRepository courseSectionRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    public List<StudentCourseSection> getAll() {
        List<StudentCourseSection> list = studentCourseSectionRepository.findByIsActiveTrue();
        return enrichWithDisplayNames(list);
    }

    public List<StudentCourseSection> getAllActive() {
        List<StudentCourseSection> list = studentCourseSectionRepository.findByIsActiveTrue();
        return enrichWithDisplayNames(list);
    }

    public StudentCourseSection getById(UUID id) {
        StudentCourseSection scs = studentCourseSectionRepository.findById(id).orElse(null);
        if (scs != null) {
            return enrichWithDisplayName(scs);
        }
        return null;
    }
    
    private StudentCourseSection enrichWithDisplayName(StudentCourseSection scs) {
        if (scs.getStudentId() != null) {
            Student student = studentRepository.findById(scs.getStudentId()).orElse(null);
            if (student != null) {
                scs.setStudentName(student.getFirstName() + " " + student.getLastName());
                scs.setStudentCode(student.getCode());
            }
        }
        if (scs.getCourseSectionId() != null) {
            CourseSection cs = courseSectionRepository.findById(scs.getCourseSectionId()).orElse(null);
            if (cs != null) {
                scs.setCourseSectionCode(cs.getCode());
                if (cs.getCourseId() != null) {
                    Course course = courseRepository.findById(cs.getCourseId()).orElse(null);
                    if (course != null) scs.setCourseName(course.getName());
                }
            }
        }
        return scs;
    }
    
    private List<StudentCourseSection> enrichWithDisplayNames(List<StudentCourseSection> list) {
        for (StudentCourseSection scs : list) {
            enrichWithDisplayName(scs);
        }
        return list;
    }

    public List<StudentCourseSection> getByCourseSectionId(UUID courseSectionId) {
        return studentCourseSectionRepository.findByCourseSectionId(courseSectionId);
    }

    public List<StudentCourseSection> getByStudentId(UUID studentId) {
        return studentCourseSectionRepository.findByStudentId(studentId);
    }

    public Long countByCourseSectionId(UUID courseSectionId) {
        return studentCourseSectionRepository.countByCourseSectionId(courseSectionId);
    }

    @Transactional
    public StudentCourseSection create(StudentCourseSection studentCourseSection) {
        studentCourseSection.setCreatedAt(LocalDateTime.now());
        studentCourseSection.setRegistrationDate(LocalDateTime.now());
        studentCourseSection.setIsActive(true);
        if (studentCourseSection.getStatus() == null) {
            studentCourseSection.setStatus("PENDING");
        }
        return studentCourseSectionRepository.save(studentCourseSection);
    }

    @Transactional
    public StudentCourseSection update(UUID id, StudentCourseSection studentCourseSection) {
        StudentCourseSection existing = studentCourseSectionRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStudentId(studentCourseSection.getStudentId());
            existing.setCourseSectionId(studentCourseSection.getCourseSectionId());
            existing.setStatus(studentCourseSection.getStatus());
            existing.setGrade(studentCourseSection.getGrade());
            existing.setGradeScore(studentCourseSection.getGradeScore());
            existing.setNote(studentCourseSection.getNote());
            existing.setUpdatedAt(LocalDateTime.now());
            return studentCourseSectionRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        StudentCourseSection studentCourseSection = studentCourseSectionRepository.findById(id).orElse(null);
        if (studentCourseSection != null) {
            studentCourseSection.setIsActive(false);
            studentCourseSectionRepository.save(studentCourseSection);
        }
    }
}