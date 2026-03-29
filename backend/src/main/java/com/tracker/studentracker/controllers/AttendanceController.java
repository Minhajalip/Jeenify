package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.AttendanceRecord;
import com.tracker.studentracker.models.AttendanceSession;
import com.tracker.studentracker.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/session")
    public ResponseEntity<?> createSession(@RequestBody AttendanceSession session) {
        try {
            AttendanceSession created = attendanceService.createSession(session);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody List<AttendanceRecord> records) {
        try {
            attendanceService.markAttendance(records);
            return ResponseEntity.ok("Attendance marked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}