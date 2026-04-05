package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.models.AttendanceClaim;
import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.services.AttendanceClaimService;
import com.tracker.studentracker.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance/claims")
@CrossOrigin(origins = "*")
public class AttendanceClaimController {

    @Autowired
    private AttendanceClaimService claimService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private StudentServices studentServices;

    // STUDENT - submit a claim
    @PostMapping
    public ResponseEntity<?> submitClaim(@RequestBody AttendanceClaim claim) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != claim.getStudentId()) {
                    return ResponseEntity.status(403).body("Access denied: you can only submit claims for yourself");
                }
            }
            AttendanceClaim created = claimService.submitClaim(claim);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - view own claims only
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getClaimsByStudent(@PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only view your own claims");
                }
            }
            List<AttendanceClaim> claims = claimService.getClaimsByStudent(studentId);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - delete own claim only (only if pending)
    @DeleteMapping("/{claimId}/student/{studentId}")
    public ResponseEntity<?> deleteClaim(@PathVariable int claimId, @PathVariable int studentId) {
        try {
            String role = authHelper.getCurrentRole();
            if (role.equals("STUDENT")) {
                Long userId = authHelper.getCurrentUserId();
                Student student = studentServices.getStudentByUserId(userId);
                if (student.getStudentId() != studentId) {
                    return ResponseEntity.status(403).body("Access denied: you can only delete your own claims");
                }
            }
            claimService.deleteClaim(claimId, studentId);
            return ResponseEntity.ok("Claim deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - view all claims
    @GetMapping
    public ResponseEntity<?> getAllClaims() {
        try {
            List<AttendanceClaim> claims = claimService.getAllClaims();
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - view claims for a session
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<?> getClaimsBySession(@PathVariable int sessionId) {
        try {
            List<AttendanceClaim> claims = claimService.getClaimsBySession(sessionId);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - approve a claim
    @PutMapping("/{claimId}/approve")
    public ResponseEntity<?> approveClaim(@PathVariable int claimId) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            AttendanceClaim claim = claimService.approveClaim(claimId, role, currentUserId);
            return ResponseEntity.ok(claim);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{claimId}/reject")
    public ResponseEntity<?> rejectClaim(@PathVariable int claimId) {
        try {
            String role = authHelper.getCurrentRole();
            Long currentUserId = authHelper.getCurrentUserId();
            AttendanceClaim claim = claimService.rejectClaim(claimId, role, currentUserId);
            return ResponseEntity.ok(claim);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}