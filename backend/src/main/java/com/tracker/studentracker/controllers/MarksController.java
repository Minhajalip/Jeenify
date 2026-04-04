package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.AssignmentMark;
import com.tracker.studentracker.models.ExamMark;
import com.tracker.studentracker.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "*")
public class MarksController {

    @Autowired
    private MarksService marksService;

    // TEACHER/ADMIN - enter exam marks
    @PostMapping("/exam")
    public ResponseEntity<?> enterExamMarks(@RequestBody ExamMark mark) {
        try {
            ExamMark created = marksService.enterExamMarks(mark);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - update exam marks
    @PutMapping("/exam/{studentId}/{examId}")
    public ResponseEntity<?> updateExamMarks(
            @PathVariable int studentId,
            @PathVariable int examId,
            @RequestParam int marksObtained) {
        try {
            ExamMark updated = marksService.updateExamMarks(studentId, examId, marksObtained);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - get all marks for an exam
    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getMarksByExam(@PathVariable int examId) {
        try {
            return ResponseEntity.ok(marksService.getMarksByExam(examId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - get own exam marks
    @GetMapping("/exam/student/{studentId}")
    public ResponseEntity<?> getExamMarksByStudent(@PathVariable int studentId) {
        try {
            return ResponseEntity.ok(marksService.getExamMarksByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - enter assignment marks
    @PostMapping("/assignment")
    public ResponseEntity<?> enterAssignmentMarks(@RequestBody AssignmentMark mark) {
        try {
            AssignmentMark created = marksService.enterAssignmentMarks(mark);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - update assignment marks
    @PutMapping("/assignment/{studentId}/{assignmentId}")
    public ResponseEntity<?> updateAssignmentMarks(
            @PathVariable int studentId,
            @PathVariable int assignmentId,
            @RequestParam int marksObtained) {
        try {
            AssignmentMark updated = marksService.updateAssignmentMarks(studentId, assignmentId, marksObtained);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - get all marks for an assignment
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<?> getMarksByAssignment(@PathVariable int assignmentId) {
        try {
            return ResponseEntity.ok(marksService.getMarksByAssignment(assignmentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - get own assignment marks
    @GetMapping("/assignment/student/{studentId}")
    public ResponseEntity<?> getAssignmentMarksByStudent(@PathVariable int studentId) {
        try {
            return ResponseEntity.ok(marksService.getAssignmentMarksByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}