package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CourseSection;
import com.example.demo.model.LecturerCourseClass;
import com.example.demo.repository.CourseSectionRepository;
import com.example.demo.repository.LecturerCourseClassRepository;
import com.example.demo.repository.IntakeRepository;
import com.example.demo.repository.SchoolYearRepository;
import com.example.demo.repository.SemesterRepository;
import com.example.demo.repository.StudentCourseSectionRepository;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardApiController {

    @Autowired
    private SchoolYearRepository schoolYearRepository;
    
    @Autowired
    private SemesterRepository semesterRepository;
    
    @Autowired
    private CourseSectionRepository courseSectionRepository;
    
    @Autowired
    private StudentCourseSectionRepository studentCourseSectionRepository;
    
    @Autowired
    private LecturerCourseClassRepository lecturerCourseClassRepository;

    @Autowired
    private IntakeRepository intakeRepository;

    // 3.1. Danh sách lớp học phần đầy đủ thông tin năm học/học kỳ
    @GetMapping("/course-sections-full")
    public ResponseEntity<List<Map<String, Object>>> getCourseSectionsFullInfo() {
        List<Map<String, Object>> result = new ArrayList<>();
        
        List<CourseSection> courseSections = courseSectionRepository.findByIsActiveTrue();
        
        for (CourseSection cs : courseSections) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", cs.getId());
            item.put("maLop", cs.getCode());
            item.put("tenMon", cs.getName());
            item.put("siSoToiDa", cs.getMaxStudents());
            item.put("trangThai", cs.getStatus());
            
            // Lấy thông tin học kỳ
            if (cs.getSemesterId() != null) {
                semesterRepository.findById(cs.getSemesterId()).ifPresent(sm -> {
                    item.put("hocKyId", sm.getId());
                    item.put("hocKy", sm.getName());
                    
                    // Lấy thông tin năm học
                    if (sm.getSchoolYearId() != null) {
                        schoolYearRepository.findById(sm.getSchoolYearId()).ifPresent(sy -> {
                            item.put("namHocId", sy.getId());
                            item.put("namHoc", sy.getName());
                        });
                    }
                });
            }
            
            // Đếm số sinh viên đăng ký
            Long soSinhVien = studentCourseSectionRepository.countByCourseSectionId(cs.getId());
            item.put("soSinhVienDangKy", soSinhVien);
            
            result.add(item);
        }
        
        return ResponseEntity.ok(result);
    }

    // 3.2. Thống kê sĩ số thực tế của từng lớp
    @GetMapping("/course-sections-statistics")
    public ResponseEntity<List<Map<String, Object>>> getCourseSectionsStatistics() {
        List<Map<String, Object>> result = new ArrayList<>();
        
        List<CourseSection> courseSections = courseSectionRepository.findByIsActiveTrue();
        
        for (CourseSection cs : courseSections) {
            Map<String, Object> item = new HashMap<>();
            item.put("maLop", cs.getCode());
            item.put("tenMon", cs.getName());
            
            Long soSVDangKy = studentCourseSectionRepository.countByCourseSectionId(cs.getId());
            Integer chieuToiDa = cs.getMaxStudents() != null ? cs.getMaxStudents() : 0;
            
            item.put("soSVDangKy", soSVDangKy);
            item.put("chiTieu", chieuToiDa);
            item.put("conTrong", chieuToiDa - soSVDangKy.intValue());
            
            result.add(item);
        }
        
        return ResponseEntity.ok(result);
    }

    // 3.3. Danh sách phân công giảng dạy
    @GetMapping("/lecturer-assignments")
    public ResponseEntity<List<Map<String, Object>>> getLecturerAssignments() {
        List<Map<String, Object>> result = new ArrayList<>();
        
        List<LecturerCourseClass> assignments = lecturerCourseClassRepository.findByIsActiveTrue();
        
        for (LecturerCourseClass lcc : assignments) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", lcc.getId());
            item.put("maGV", lcc.getEmployeeId());
            item.put("maLopHocPhan", lcc.getCourseSectionId());
            item.put("vaiTro", lcc.getRole());
            
            // Lấy thông tin lớp học phần
            if (lcc.getCourseSectionId() != null) {
                courseSectionRepository.findById(lcc.getCourseSectionId()).ifPresent(cs -> {
                    item.put("maLop", cs.getCode());
                    item.put("tenMon", cs.getName());
                });
            }
            
            result.add(item);
        }
        
        return ResponseEntity.ok(result);
    }

    // Thống kê tổng quan
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        overview.put("tongNamHoc", schoolYearRepository.count());
        overview.put("tongHocKy", semesterRepository.count());
        overview.put("tongLopHocPhan", courseSectionRepository.count());
        overview.put("tongPhanCongGiangDay", lecturerCourseClassRepository.count());
        overview.put("tongNienKhoa", intakeRepository.count());
        
        return ResponseEntity.ok(overview);
    }
}