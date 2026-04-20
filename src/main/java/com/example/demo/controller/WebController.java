package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("pageTitle", "Tổng quan");
        return "dashboard";
    }

    @GetMapping("/academic-years")
    public String academicYears(Model model) {
        model.addAttribute("activePage", "academic-years");
        model.addAttribute("pageTitle", "Quản lý Năm học");
        return "academic_years";
    }

    @GetMapping("/school-years")
    public String schoolYears(Model model) {
        model.addAttribute("activePage", "school-years");
        model.addAttribute("pageTitle", "Quản lý Năm thứ");
        return "school_years";
    }

    @GetMapping("/semesters")
    public String semesters(Model model) {
        model.addAttribute("activePage", "semesters");
        model.addAttribute("pageTitle", "Quản lý Học kỳ");
        return "semesters";
    }

    @GetMapping("/course-sections")
    public String courseSections(Model model) {
        model.addAttribute("activePage", "course-sections");
        model.addAttribute("pageTitle", "Quản lý Lớp học phần");
        return "course_sections";
    }

    @GetMapping("/student-registrations")
    public String studentRegistrations(Model model) {
        model.addAttribute("activePage", "student-registrations");
        model.addAttribute("pageTitle", "Đăng ký Sinh viên");
        return "student_registrations";
    }

    @GetMapping("/lecturer-assignments")
    public String lecturerAssignments(Model model) {
        model.addAttribute("activePage", "lecturer-assignments");
        model.addAttribute("pageTitle", "Phân công Giảng viên");
        return "lecturer_assignments";
    }

    @GetMapping("/intakes")
    public String intakes(Model model) {
        model.addAttribute("activePage", "intakes");
        model.addAttribute("pageTitle", "Quản lý Niên khóa");
        return "intakes";
    }
}