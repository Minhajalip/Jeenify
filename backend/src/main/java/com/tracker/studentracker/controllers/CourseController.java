package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.Course;
import com.tracker.studentracker.models.CourseTeacher;
import com.tracker.studentracker.repository.CourseRepository;
import com.tracker.studentracker.repository.CourseTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @DeleteMapping
    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }

    @PostMapping("/{courseId}/assign-teacher/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignTeacher(@PathVariable int courseId, @PathVariable int teacherId) {
        try {
            if(courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacherId)){
                return ResponseEntity.badRequest().body("Teacher already assigned to this course");
            }
            CourseTeacher ct = new CourseTeacher();
            ct.setCourseId(courseId);
            ct.setTeacherId(teacherId);
            courseTeacherRepository.save(ct);
            return ResponseEntity.ok("Teacher assigned to course successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{courseId}/remove-teacher/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeTeacher(@PathVariable int courseId, @PathVariable int teacherId) {
        try {
            if(!courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacherId)){
                return ResponseEntity.badRequest().body("Teacher is not assigned to this course");
            }
            courseTeacherRepository.deleteByCourseIdAndTeacherId(courseId, teacherId);
            return ResponseEntity.ok("Teacher removed from course successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}