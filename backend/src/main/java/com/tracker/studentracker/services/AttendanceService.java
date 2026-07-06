package com.tracker.studentracker.services;

import com.tracker.studentracker.models.AttendanceRecord;
import com.tracker.studentracker.models.AttendanceSession;
import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.AttendanceRecordRepository;
import com.tracker.studentracker.repository.AttendanceSessionRepository;
import com.tracker.studentracker.repository.CourseTeacherRepository;
import com.tracker.studentracker.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceSessionRepository sessionRepo;

    @Autowired
    private AttendanceRecordRepository recordRepo;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public AttendanceSession createSession(AttendanceSession session, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            Teacher teacher = teacherRepository.findByUserId(currentUserId);
            if (teacher == null) throw new RuntimeException("Teacher profile not found");
            if (!courseTeacherRepository.existsByCourseIdAndTeacherId(session.getCourseId().intValue(), teacher.getId().intValue())) {
                throw new RuntimeException("Access denied: you are not assigned to this course");
            }
            session.setTeacherId(teacher.getId());
        } else if (role.equals("ADMIN")) {
            // admin must explicitly provide a teacherId in the request
            if (session.getTeacherId() == null) {
                throw new RuntimeException("teacherId is required when creating a session as admin");
            }
        }
        return sessionRepo.save(session);
    }

    public void markAttendance(List<AttendanceRecord> records, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            Teacher teacher = teacherRepository.findByUserId(currentUserId);
            if (teacher == null) throw new RuntimeException("Teacher profile not found");

            for (AttendanceRecord record : records) {
                // check that this session belongs to a course this teacher owns
                AttendanceSession session = sessionRepo.findById(record.getSessionId())
                        .orElseThrow(() -> new RuntimeException("Session not found: " + record.getSessionId()));
                if (!courseTeacherRepository.existsByCourseIdAndTeacherId(session.getCourseId().intValue(), teacher.getId().intValue())) {
                    throw new RuntimeException("Access denied: you are not assigned to course for session " + record.getSessionId());
                }
            }
        }

        // duplicate check for all records
        for (AttendanceRecord record : records) {
            if (recordRepo.existsBySessionIdAndStudentId(record.getSessionId(), record.getStudentId())) {
                throw new RuntimeException("Attendance already marked for student " + record.getStudentId() + " in session " + record.getSessionId());
            }
        }

        recordRepo.saveAll(records);
    }
}