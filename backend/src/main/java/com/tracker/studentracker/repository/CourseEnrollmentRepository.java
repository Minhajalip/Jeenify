package com.tracker.studentracker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseEnrollmentRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void save(int studentId, int courseId){
        String sql = "INSERT INTO course_enrollments(student_id, course_id, enrollment_date) VALUES(?,?,CURDATE())";
        jdbc.update(sql, studentId, courseId);
    }
}
