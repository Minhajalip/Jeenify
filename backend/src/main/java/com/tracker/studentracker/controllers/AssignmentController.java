package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.Assignment;
import com.tracker.studentracker.services.AssignmentService;
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

    // TEACHER/ADMIN - create assignment
    @PostMapping
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        try {
            Assignment created = assignmentService.createAssignment(assignment);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - get all assignments for a course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getAssignmentsForCourse(@PathVariable int courseId) {
        try {
            List<Assignment> assignments = assignmentService.getAssignmentsForCourse(courseId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - update assignment
    @PutMapping("/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable int assignmentId, @RequestBody Assignment assignment) {
        try {
            Assignment updated = assignmentService.updateAssignment(assignmentId, assignment);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - delete assignment
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable int assignmentId) {
        try {
            assignmentService.deleteAssignment(assignmentId);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - get assignments for enrolled courses
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAssignmentsForStudent(@PathVariable int studentId) {
        try {
            List<Assignment> assignments = assignmentService.getAssignmentsForStudent(studentId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}