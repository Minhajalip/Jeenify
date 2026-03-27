package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public int save(Student student){
        String sql = "INSERT INTO students(student_id,user_id,student_number,major_course_id) VALUES(?,?,?,?)";

        return jdbc.update(sql,
                student.getStudentId(),
                student.getUserId(),
                student.getStudentNumber(),
                student.getMajorCourseId()
        );
    }

    public boolean existsByStudentId(int studentId){
        String sql = "SELECT COUNT(*) FROM students WHERE student_id=?";
        Integer count = jdbc.queryForObject(sql, Integer.class, studentId);
        return count != null && count > 0;
    }

    public Student findById(int studentId){

        String sql = "SELECT * FROM students WHERE student_id=?";

        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            Student s = new Student();
            s.setStudentId(rs.getInt("student_id"));
            s.setUserId(rs.getInt("user_id"));
            s.setStudentNumber(rs.getString("student_number"));
            s.setMajorCourseId(rs.getInt("major_course_id"));
            s.setStatus(Student.Status.valueOf(rs.getString("approval_status")));
            return s;
        }, studentId);
    }
}
