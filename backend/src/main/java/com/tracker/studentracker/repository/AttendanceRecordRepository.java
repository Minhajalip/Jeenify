package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
}