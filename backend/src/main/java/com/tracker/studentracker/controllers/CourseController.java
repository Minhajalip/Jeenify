package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.Course;
import com.tracker.studentracker.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // ✅ GET all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // ✅ POST create course
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    // ✅ DELETE all courses
    @DeleteMapping
    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }
}