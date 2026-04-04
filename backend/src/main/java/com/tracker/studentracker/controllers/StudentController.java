package com.tracker.studentracker.controllers;

import com.tracker.studentracker.dto.StudentRegisterRequest;
import com.tracker.studentracker.dto.CourseSelectionRequest;
import com.tracker.studentracker.services.StudentServices;
import com.tracker.studentracker.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private UserServices userService;

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
    public ResponseEntity<String> selectCourses(@RequestBody CourseSelectionRequest request){
        try{

            studentServices.selectCourses(
                    request.getStudentId(),
                    request.getCourseIds()
            );

            return ResponseEntity.ok("Courses selected successfully");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
