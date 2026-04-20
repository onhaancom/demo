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
import com.example.demo.model.Room;
import com.example.demo.model.Semester;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseSectionRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.SemesterRepository;

@Service
public class CourseSectionService {

    @Autowired
    private CourseSectionRepository courseSectionRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private SemesterRepository semesterRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private RoomRepository roomRepository;

    public List<CourseSection> getAll() {
        List<CourseSection> list = courseSectionRepository.findByIsActiveTrue();
        return enrichWithDisplayNames(list);
    }

    public List<CourseSection> getAllActive() {
        List<CourseSection> list = courseSectionRepository.findByIsActiveTrue();
        return enrichWithDisplayNames(list);
    }

    public CourseSection getById(UUID id) {
        CourseSection cs = courseSectionRepository.findById(id).orElse(null);
        if (cs != null) {
            return enrichWithDisplayName(cs);
        }
        return null;
    }
    
    private CourseSection enrichWithDisplayName(CourseSection cs) {
        if (cs.getCourseId() != null) {
            Course course = courseRepository.findById(cs.getCourseId()).orElse(null);
            if (course != null) cs.setCourseName(course.getName());
        }
        if (cs.getSemesterId() != null) {
            Semester semester = semesterRepository.findById(cs.getSemesterId()).orElse(null);
            if (semester != null) cs.setSemesterName(semester.getName());
        }
        if (cs.getLecturerId() != null) {
            Employee emp = employeeRepository.findById(cs.getLecturerId()).orElse(null);
            if (emp != null) cs.setLecturerName(emp.getFirstName() + " " + emp.getLastName());
        }
        if (cs.getRoomId() != null) {
            Room room = roomRepository.findById(cs.getRoomId()).orElse(null);
            if (room != null) cs.setRoomName(room.getName());
        }
        return cs;
    }
    
    private List<CourseSection> enrichWithDisplayNames(List<CourseSection> list) {
        for (CourseSection cs : list) {
            enrichWithDisplayName(cs);
        }
        return list;
    }

    public List<CourseSection> getBySemesterId(UUID semesterId) {
        return courseSectionRepository.findBySemesterId(semesterId);
    }

    public List<CourseSection> getByCourseId(UUID courseId) {
        return courseSectionRepository.findByCourseId(courseId);
    }

    @Transactional
    public CourseSection create(CourseSection courseSection) {
        courseSection.setCreatedAt(LocalDateTime.now());
        courseSection.setIsActive(true);
        if (courseSection.getCurrentStudents() == null) {
            courseSection.setCurrentStudents(0);
        }
        if (courseSection.getStatus() == null) {
            courseSection.setStatus("OPEN");
        }
        return courseSectionRepository.save(courseSection);
    }

    @Transactional
    public CourseSection update(UUID id, CourseSection courseSection) {
        CourseSection existing = courseSectionRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCode(courseSection.getCode());
            existing.setName(courseSection.getName());
            existing.setCourseId(courseSection.getCourseId());
            existing.setSemesterId(courseSection.getSemesterId());
            existing.setLecturerId(courseSection.getLecturerId());
            existing.setRoomId(courseSection.getRoomId());
            existing.setMaxStudents(courseSection.getMaxStudents());
            existing.setMinStudents(courseSection.getMinStudents());
            existing.setCurrentStudents(courseSection.getCurrentStudents());
            existing.setClassType(courseSection.getClassType());
            existing.setStatus(courseSection.getStatus());
            existing.setScheduleInfo(courseSection.getScheduleInfo());
            existing.setNote(courseSection.getNote());
            existing.setUpdatedAt(LocalDateTime.now());
            return courseSectionRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        CourseSection courseSection = courseSectionRepository.findById(id).orElse(null);
        if (courseSection != null) {
            courseSection.setIsActive(false);
            courseSectionRepository.save(courseSection);
        }
    }
}