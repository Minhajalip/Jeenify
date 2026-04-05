package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
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

    @Autowired
    private AuthHelper authHelper;

    @PostMapping("/session")
    public ResponseEntity<?> createSession(@RequestBody AttendanceSession session) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            AttendanceSession created = attendanceService.createSession(session, role, currentUserId);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody List<AttendanceRecord> records) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            attendanceService.markAttendance(records, role, currentUserId);
            return ResponseEntity.ok("Attendance marked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}