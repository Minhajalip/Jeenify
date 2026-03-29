package com.tracker.studentracker.services;

import com.tracker.studentracker.models.AttendanceRecord;
import com.tracker.studentracker.models.AttendanceSession;
import com.tracker.studentracker.repository.AttendanceRecordRepository;
import com.tracker.studentracker.repository.AttendanceSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceSessionRepository sessionRepo;

    @Autowired
    private AttendanceRecordRepository recordRepo;

    public AttendanceSession createSession(AttendanceSession session) {
        return sessionRepo.save(session);
    }

    public void markAttendance(List<AttendanceRecord> records) {
        recordRepo.saveAll(records);
    }
}