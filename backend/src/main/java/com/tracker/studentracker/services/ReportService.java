package com.tracker.studentracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private JdbcTemplate jdbc;

    // Attendance percentage for a student in a course
    public Map<String, Object> getAttendanceReport(int studentId, int courseId) {
        String sql = """
                SELECT
                    COUNT(*) as total_sessions,
                    SUM(CASE WHEN ar.status = 'Present' THEN 1 ELSE 0 END) as present_count,
                    ROUND(SUM(CASE WHEN ar.status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) as attendance_percentage
                FROM attendance_records ar
                JOIN attendance_sessions as_ ON ar.session_id = as_.id
                WHERE ar.student_id = ? AND as_.course_id = ?
                """;
        return jdbc.queryForMap(sql, studentId, courseId);
    }

    // Attendance report for all students in a course
    public List<Map<String, Object>> getAttendanceReportForCourse(int courseId) {
        String sql = """
                SELECT
                    ar.student_id,
                    COUNT(*) as total_sessions,
                    SUM(CASE WHEN ar.status = 'Present' THEN 1 ELSE 0 END) as present_count,
                    ROUND(SUM(CASE WHEN ar.status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) as attendance_percentage
                FROM attendance_records ar
                JOIN attendance_sessions as_ ON ar.session_id = as_.id
                WHERE as_.course_id = ?
                GROUP BY ar.student_id
                """;
        return jdbc.queryForList(sql, courseId);
    }

    // Exam marks summary for a student
    public List<Map<String, Object>> getExamReport(int studentId) {
        String sql = """
                SELECT
                    e.title as exam_title,
                    e.max_marks,
                    em.marks_obtained,
                    ROUND(em.marks_obtained * 100.0 / e.max_marks, 2) as percentage
                FROM exam_marks em
                JOIN exams e ON em.exam_id = e.exam_id
                WHERE em.student_id = ?
                """;
        return jdbc.queryForList(sql, studentId);
    }

    // Exam marks summary for all students in a course
    public List<Map<String, Object>> getExamReportForCourse(int courseId) {
        String sql = """
                SELECT
                    em.student_id,
                    e.title as exam_title,
                    e.max_marks,
                    em.marks_obtained,
                    ROUND(em.marks_obtained * 100.0 / e.max_marks, 2) as percentage
                FROM exam_marks em
                JOIN exams e ON em.exam_id = e.exam_id
                WHERE e.course_id = ?
                """;
        return jdbc.queryForList(sql, courseId);
    }

    // Assignment marks summary for a student
    public List<Map<String, Object>> getAssignmentReport(int studentId) {
        String sql = """
                SELECT
                    a.title as assignment_title,
                    a.max_marks,
                    am.marks_obtained,
                    ROUND(am.marks_obtained * 100.0 / a.max_marks, 2) as percentage
                FROM assignment_marks am
                JOIN assignments a ON am.assignment_id = a.assignment_id
                WHERE am.student_id = ?
                """;
        return jdbc.queryForList(sql, studentId);
    }

    // Assignment marks summary for all students in a course
    public List<Map<String, Object>> getAssignmentReportForCourse(int courseId) {
        String sql = """
                SELECT
                    am.student_id,
                    a.title as assignment_title,
                    a.max_marks,
                    am.marks_obtained,
                    ROUND(am.marks_obtained * 100.0 / a.max_marks, 2) as percentage
                FROM assignment_marks am
                JOIN assignments a ON am.assignment_id = a.assignment_id
                WHERE a.course_id = ?
                """;
        return jdbc.queryForList(sql, courseId);
    }
}