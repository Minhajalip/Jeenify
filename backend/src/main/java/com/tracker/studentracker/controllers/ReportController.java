package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.services.ReportService;
import com.tracker.studentracker.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private StudentServices studentServices;

    @GetMapping("/attendance/student/{studentId}/course/{courseId}")
    public ResponseEntity<?> getAttendanceReport(
            @PathVariable int studentId,
            @PathVariable int courseId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own attendance report");
                }
            }
            return ResponseEntity.ok(reportService.getAttendanceReport(studentId, courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/attendance/course/{courseId}")
    public ResponseEntity<?> getAttendanceReportForCourse(@PathVariable int courseId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            reportService.checkTeacherOwnership(courseId, role, currentUserId);
            return ResponseEntity.ok(reportService.getAttendanceReportForCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/exams/student/{studentId}")
    public ResponseEntity<?> getExamReport(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own exam report");
                }
            }
            return ResponseEntity.ok(reportService.getExamReport(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/exams/course/{courseId}")
    public ResponseEntity<?> getExamReportForCourse(@PathVariable int courseId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            reportService.checkTeacherOwnership(courseId, role, currentUserId);
            return ResponseEntity.ok(reportService.getExamReportForCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/assignments/student/{studentId}")
    public ResponseEntity<?> getAssignmentReport(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own assignment report");
                }
            }
            return ResponseEntity.ok(reportService.getAssignmentReport(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/assignments/course/{courseId}")
    public ResponseEntity<?> getAssignmentReportForCourse(@PathVariable int courseId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            reportService.checkTeacherOwnership(courseId, role, currentUserId);
            return ResponseEntity.ok(reportService.getAssignmentReportForCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}