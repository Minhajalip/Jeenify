package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    boolean existsByIdAndTeacherId(Long sessionId, Long teacherId);
}