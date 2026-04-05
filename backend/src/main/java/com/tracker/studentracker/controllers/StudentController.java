package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.dto.StudentRegisterRequest;
import com.tracker.studentracker.dto.CourseSelectionRequest;
import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.services.StudentServices;
import com.tracker.studentracker.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private UserServices userService;

    @Autowired
    private AuthHelper authHelper;

    // 1. Student Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentRegisterRequest request){
        try{
            Long userId = userService.registerStudentUser(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword()
            );
            studentServices.createStudent(
                    userId,
                    request.getStudentNumber(),
                    request.getMajorCourseId()
            );
            return ResponseEntity.ok("Student registered successfully. Pending approval.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 2. Course Selection (AFTER approval)
    @PostMapping("/select-courses")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<String> selectCourses(@RequestBody CourseSelectionRequest request) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();

            studentServices.selectCourses(
                    request.getStudentId(),
                    request.getCourseIds(),
                    role,
                    currentUserId
            );
            return ResponseEntity.ok("Courses selected successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 3. Approve Student (ADMIN only)
    @PutMapping("/approve/{studentId}")
    public ResponseEntity<String> approveStudent(@PathVariable int studentId){
        try{
            studentServices.approveStudent(studentId);
            return ResponseEntity.ok("Student approved successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 4. Get student profile by userId
    @GetMapping("/me/{userId}")
    public ResponseEntity<?> getStudentByUserId(@PathVariable Long userId){
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();

            // Students can only view their own profile
            if (role.equals("STUDENT") && !currentUserId.equals(userId)) {
                return ResponseEntity.status(403).body("Access denied: you can only view your own profile");
            }

            Student student = studentServices.getStudentByUserId(userId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}