package com.tracker.studentracker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseEnrollmentRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void save(int studentId, int courseId){
        String sql = "INSERT INTO course_enrollments(student_id, course_id) VALUES(?,?)";
        jdbc.update(sql, studentId, courseId);
    }

    public boolean isStudentEnrolled(int studentId, int courseId){
        String sql = "SELECT COUNT(*) FROM course_enrollments WHERE student_id = ? AND course_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, studentId, courseId);
        return count != null && count > 0;
    }

    public List<Integer> getEnrolledCourseIds(int studentId){
        String sql = "SELECT course_id FROM course_enrollments WHERE student_id = ?";
        return jdbc.queryForList(sql, Integer.class, studentId);
    }
}