package com.tracker.studentracker.services;

import com.tracker.studentracker.models.AttendanceClaim;
import com.tracker.studentracker.repository.AttendanceClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceClaimService {

    @Autowired
    private AttendanceClaimRepository claimRepository;

    // STUDENT - submit a claim
    public AttendanceClaim submitClaim(AttendanceClaim claim) {
        claim.setStatus("Pending");
        return claimRepository.save(claim);
    }

    // STUDENT - view own claims
    public List<AttendanceClaim> getClaimsByStudent(int studentId) {
        return claimRepository.findByStudentId(studentId);
    }

    // STUDENT - delete a claim (only if pending)
    public void deleteClaim(int claimId, int studentId) {
        AttendanceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        if (claim.getStudentId() != studentId) {
            throw new RuntimeException("You can only delete your own claims");
        }
        if (!claim.getStatus().equals("Pending")) {
            throw new RuntimeException("Cannot delete a claim that has already been reviewed");
        }
        claimRepository.deleteById(claimId);
    }

    // TEACHER/ADMIN - view all claims
    public List<AttendanceClaim> getAllClaims() {
        return claimRepository.findAll();
    }

    // TEACHER/ADMIN - view claims for a session
    public List<AttendanceClaim> getClaimsBySession(int sessionId) {
        return claimRepository.findBySessionId(sessionId);
    }

    // TEACHER/ADMIN - approve a claim
    public AttendanceClaim approveClaim(int claimId) {
        AttendanceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        if (!claim.getStatus().equals("Pending")) {
            throw new RuntimeException("Claim has already been reviewed");
        }
        claim.setStatus("Approved");
        return claimRepository.save(claim);
    }

    // TEACHER/ADMIN - reject a claim
    public AttendanceClaim rejectClaim(int claimId) {
        AttendanceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        if (!claim.getStatus().equals("Pending")) {
            throw new RuntimeException("Claim has already been reviewed");
        }
        claim.setStatus("Rejected");
        return claimRepository.save(claim);
    }
}