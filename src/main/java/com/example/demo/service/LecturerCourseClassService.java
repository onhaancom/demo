package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Course;
import com.example.demo.model.CourseSection;
import com.example.demo.model.Employee;
import com.example.demo.model.LecturerCourseClass;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseSectionRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.LecturerCourseClassRepository;

@Service
public class LecturerCourseClassService {

    @Autowired
    private LecturerCourseClassRepository lecturerCourseClassRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CourseSectionRepository courseSectionRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    public List<LecturerCourseClass> getAll() {
        List<LecturerCourseClass> list = lecturerCourseClassRepository.findByIsActiveTrue();
        return enrichWithDisplayNames(list);
    }

    public List<LecturerCourseClass> getAllActive() {
        List<LecturerCourseClass> list = lecturerCourseClassRepository.findByIsActiveTrue();
        return enrichWithDisplayNames(list);
    }

    public LecturerCourseClass getById(UUID id) {
        LecturerCourseClass lcc = lecturerCourseClassRepository.findById(id).orElse(null);
        if (lcc != null) {
            return enrichWithDisplayName(lcc);
        }
        return null;
    }
    
    private LecturerCourseClass enrichWithDisplayName(LecturerCourseClass lcc) {
        if (lcc.getEmployeeId() != null) {
            Employee emp = employeeRepository.findById(lcc.getEmployeeId()).orElse(null);
            if (emp != null) {
                lcc.setEmployeeName(emp.getFirstName() + " " + emp.getLastName());
                lcc.setEmployeeCode(emp.getCode());
            }
        }
        if (lcc.getCourseSectionId() != null) {
            CourseSection cs = courseSectionRepository.findById(lcc.getCourseSectionId()).orElse(null);
            if (cs != null) {
                lcc.setCourseSectionCode(cs.getCode());
                if (cs.getCourseId() != null) {
                    Course course = courseRepository.findById(cs.getCourseId()).orElse(null);
                    if (course != null) lcc.setCourseName(course.getName());
                }
            }
        }
        return lcc;
    }
    
    private List<LecturerCourseClass> enrichWithDisplayNames(List<LecturerCourseClass> list) {
        for (LecturerCourseClass lcc : list) {
            enrichWithDisplayName(lcc);
        }
        return list;
    }

    public List<LecturerCourseClass> getByCourseSectionId(UUID courseSectionId) {
        return lecturerCourseClassRepository.findByCourseSectionId(courseSectionId);
    }

    public List<LecturerCourseClass> getByEmployeeId(UUID employeeId) {
        return lecturerCourseClassRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    public LecturerCourseClass create(LecturerCourseClass lecturerCourseClass) {
        lecturerCourseClass.setCreatedAt(LocalDateTime.now());
        lecturerCourseClass.setIsActive(true);
        LecturerCourseClass saved = lecturerCourseClassRepository.save(lecturerCourseClass);
        return enrichWithDisplayName(saved);
    }

    @Transactional
    public LecturerCourseClass update(UUID id, LecturerCourseClass lecturerCourseClass) {
        LecturerCourseClass existing = lecturerCourseClassRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setEmployeeId(lecturerCourseClass.getEmployeeId());
            existing.setCourseSectionId(lecturerCourseClass.getCourseSectionId());
            existing.setRole(lecturerCourseClass.getRole());
            existing.setTeachingHours(lecturerCourseClass.getTeachingHours());
            existing.setIsPrimary(lecturerCourseClass.getIsPrimary());
            existing.setNote(lecturerCourseClass.getNote());
            existing.setUpdatedAt(LocalDateTime.now());
            LecturerCourseClass saved = lecturerCourseClassRepository.save(existing);
            return enrichWithDisplayName(saved);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        LecturerCourseClass lecturerCourseClass = lecturerCourseClassRepository.findById(id).orElse(null);
        if (lecturerCourseClass != null) {
            lecturerCourseClass.setIsActive(false);
            lecturerCourseClassRepository.save(lecturerCourseClass);
        }
    }
}