package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.models.Exam;
import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.services.ExamService;
import com.tracker.studentracker.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private StudentServices studentServices;

    // TEACHER/ADMIN - create exam
    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {
        try {
            Exam created = examService.createExam(exam);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - get all exams for a course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getExamsForCourse(@PathVariable int courseId) {
        try {
            List<Exam> exams = examService.getExamsForCourse(courseId);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - update exam
    @PutMapping("/{examId}")
    public ResponseEntity<?> updateExam(@PathVariable int examId, @RequestBody Exam exam) {
        try {
            Exam updated = examService.updateExam(examId, exam);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - delete exam
    @DeleteMapping("/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable int examId) {
        try {
            examService.deleteExam(examId);
            return ResponseEntity.ok("Exam deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - get exams for own enrolled courses only
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getExamsForStudent(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own exams");
                }
            }
            List<Exam> exams = examService.getExamsForStudent(studentId);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}