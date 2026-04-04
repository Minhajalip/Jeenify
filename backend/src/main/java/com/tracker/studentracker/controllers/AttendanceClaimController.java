package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.AttendanceClaim;
import com.tracker.studentracker.services.AttendanceClaimService;
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

    // STUDENT - submit a claim
    @PostMapping
    public ResponseEntity<?> submitClaim(@RequestBody AttendanceClaim claim) {
        try {
            AttendanceClaim created = claimService.submitClaim(claim);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - view own claims
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getClaimsByStudent(@PathVariable int studentId) {
        try {
            List<AttendanceClaim> claims = claimService.getClaimsByStudent(studentId);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // STUDENT - delete a claim (only if pending)
    @DeleteMapping("/{claimId}/student/{studentId}")
    public ResponseEntity<?> deleteClaim(@PathVariable int claimId, @PathVariable int studentId) {
        try {
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
            AttendanceClaim claim = claimService.approveClaim(claimId);
            return ResponseEntity.ok(claim);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // TEACHER/ADMIN - reject a claim
    @PutMapping("/{claimId}/reject")
    public ResponseEntity<?> rejectClaim(@PathVariable int claimId) {
        try {
            AttendanceClaim claim = claimService.rejectClaim(claimId);
            return ResponseEntity.ok(claim);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}