package com.tracker.studentracker.services;

import com.tracker.studentracker.models.AttendanceClaim;
import com.tracker.studentracker.models.AttendanceSession;
import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.AttendanceClaimRepository;
import com.tracker.studentracker.repository.AttendanceSessionRepository;
import com.tracker.studentracker.repository.CourseTeacherRepository;
import com.tracker.studentracker.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceClaimService {

    @Autowired
    private AttendanceClaimRepository claimRepository;

    @Autowired
    private AttendanceSessionRepository sessionRepository;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private void checkTeacherOwnershipByClaim(int claimId, Long currentUserId) {
        AttendanceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        AttendanceSession session = sessionRepository.findById((long) claim.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));
        Teacher teacher = teacherRepository.findByUserId(currentUserId);
        if (teacher == null) throw new RuntimeException("Teacher profile not found");
        if (!courseTeacherRepository.existsByCourseIdAndTeacherId(session.getCourseId().intValue(), teacher.getId().intValue())) {
            throw new RuntimeException("Access denied: you are not assigned to this course");
        }
    }

    public AttendanceClaim submitClaim(AttendanceClaim claim) {
        claim.setStatus("Pending");
        return claimRepository.save(claim);
    }

    public List<AttendanceClaim> getClaimsByStudent(int studentId) {
        return claimRepository.findByStudentId(studentId);
    }

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

    public List<AttendanceClaim> getAllClaims() {
        return claimRepository.findAll();
    }

    public List<AttendanceClaim> getClaimsBySession(int sessionId) {
        return claimRepository.findBySessionId(sessionId);
    }

    public AttendanceClaim approveClaim(int claimId, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByClaim(claimId, currentUserId);
        }
        AttendanceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        if (!claim.getStatus().equals("Pending")) {
            throw new RuntimeException("Claim has already been reviewed");
        }
        claim.setStatus("Approved");
        return claimRepository.save(claim);
    }

    public AttendanceClaim rejectClaim(int claimId, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByClaim(claimId, currentUserId);
        }
        AttendanceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        if (!claim.getStatus().equals("Pending")) {
            throw new RuntimeException("Claim has already been reviewed");
        }
        claim.setStatus("Rejected");
        return claimRepository.save(claim);
    }
}