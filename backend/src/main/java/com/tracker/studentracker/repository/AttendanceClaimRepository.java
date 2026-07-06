package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.AttendanceClaim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceClaimRepository extends JpaRepository<AttendanceClaim, Integer> {
    List<AttendanceClaim> findByStudentId(int studentId);
    List<AttendanceClaim> findBySessionId(int sessionId);
}