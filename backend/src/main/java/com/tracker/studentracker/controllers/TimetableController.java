package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.Timetable;
import com.tracker.studentracker.services.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
@CrossOrigin(origins = "*")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    // Get all timetable slots
    @GetMapping
    public ResponseEntity<?> getAllTimetables() {
        try {
            return ResponseEntity.ok(timetableService.getAllTimetables());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get timetable slot by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTimetableById(@PathVariable Long id) {
        try {
            Timetable timetable = timetableService.getTimetableById(id);
            if (timetable == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(timetable);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get timetable by course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getTimetableByCourse(@PathVariable int courseId) {
        try {
            return ResponseEntity.ok(timetableService.getTimetableByCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get timetable by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getTimetableByTeacher(@PathVariable int teacherId) {
        try {
            return ResponseEntity.ok(timetableService.getTimetableByTeacher(teacherId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get timetable by day
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<?> getTimetableByDay(@PathVariable String dayOfWeek) {
        try {
            return ResponseEntity.ok(timetableService.getTimetableByDay(dayOfWeek));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Add timetable slot (ADMIN only)
    @PostMapping
    public ResponseEntity<?> addTimetable(@RequestBody Timetable timetable) {
        try {
            Timetable created = timetableService.addTimetable(timetable);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Update timetable slot (ADMIN only)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTimetable(@PathVariable Long id, @RequestBody Timetable timetable) {
        try {
            timetable.setId(id);
            boolean updated = timetableService.updateTimetable(timetable);
            if (!updated) return ResponseEntity.notFound().build();
            return ResponseEntity.ok("Timetable updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Delete timetable slot (ADMIN only)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimetable(@PathVariable Long id) {
        try {
            timetableService.deleteTimetable(id);
            return ResponseEntity.ok("Timetable deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}