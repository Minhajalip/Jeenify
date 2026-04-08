package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.models.Assignment;
import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.services.AssignmentService;
import com.tracker.studentracker.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private StudentServices studentServices;

    @PostMapping
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            Assignment created = assignmentService.createAssignment(assignment, role, currentUserId);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getAssignmentsForCourse(@PathVariable int courseId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            List<Assignment> assignments = assignmentService.getAssignmentsForCourse(courseId, role, currentUserId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable int assignmentId, @RequestBody Assignment assignment) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            Assignment updated = assignmentService.updateAssignment(assignmentId, assignment, role, currentUserId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable int assignmentId) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            assignmentService.deleteAssignment(assignmentId, role, currentUserId);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAssignmentsForStudent(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own assignments");
                }
            }
            List<Assignment> assignments = assignmentService.getAssignmentsForStudent(studentId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<?> getAssignmentById(@PathVariable int assignmentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                return ResponseEntity.status(403).body("Access denied");
            }
            Long currentUserId = authHelper.getCurrentUserId();
            Assignment assignment = assignmentService.getAssignmentById(assignmentId, role, currentUserId);
            return ResponseEntity.ok(assignment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}