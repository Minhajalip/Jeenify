package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.models.AssignmentMark;
import com.tracker.studentracker.models.ExamMark;
import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.services.MarksService;
import com.tracker.studentracker.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "*")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private StudentServices studentServices;

    @PostMapping("/exam")
    public ResponseEntity<?> enterExamMarks(@RequestBody ExamMark mark) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            ExamMark created = marksService.enterExamMarks(mark, role, currentUserId);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/exam/{studentId}/{examId}")
    public ResponseEntity<?> updateExamMarks(
            @PathVariable int studentId,
            @PathVariable int examId,
            @RequestParam int marksObtained) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            ExamMark updated = marksService.updateExamMarks(studentId, examId, marksObtained, role, currentUserId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getMarksByExam(@PathVariable int examId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            return ResponseEntity.ok(marksService.getMarksByExam(examId, role, currentUserId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/exam/student/{studentId}")
    public ResponseEntity<?> getExamMarksByStudent(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own marks");
                }
            }
            return ResponseEntity.ok(marksService.getExamMarksByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/assignment")
    public ResponseEntity<?> enterAssignmentMarks(@RequestBody AssignmentMark mark) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            AssignmentMark created = marksService.enterAssignmentMarks(mark, role, currentUserId);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/assignment/{studentId}/{assignmentId}")
    public ResponseEntity<?> updateAssignmentMarks(
            @PathVariable int studentId,
            @PathVariable int assignmentId,
            @RequestParam int marksObtained) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            AssignmentMark updated = marksService.updateAssignmentMarks(studentId, assignmentId, marksObtained, role, currentUserId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<?> getMarksByAssignment(@PathVariable int assignmentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            return ResponseEntity.ok(marksService.getMarksByAssignment(assignmentId, role, currentUserId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/assignment/student/{studentId}")
    public ResponseEntity<?> getAssignmentMarksByStudent(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own marks");
                }
            }
            return ResponseEntity.ok(marksService.getAssignmentMarksByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}