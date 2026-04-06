package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.Student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public int save(Student student){
        String sql = "INSERT INTO students(user_id, student_number, major_course_id, status) VALUES(?,?,?,?)";

        return jdbc.update(sql,
                student.getUserId(),
                student.getStudentNumber(),
                student.getMajorCourseId(),
                "pending"
        );
    }
    public int approveStudent(int studentId){
       String sql = "UPDATE students SET status = 'approved' WHERE id = ?";
       return jdbc.update(sql, studentId);
   }

    public boolean existsByStudentId(int studentId){
        String sql = "SELECT COUNT(*) FROM students WHERE id=?";
        Integer count = jdbc.queryForObject(sql, Integer.class, studentId);
        return count != null && count > 0;
    }

    public Student findById(Long studentId){

        String sql = "SELECT * FROM students WHERE id=?";

        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            Student s = new Student();
            s.setStudentId(rs.getInt("id"));
            s.setUserId(rs.getLong("user_id"));
            s.setStudentNumber(rs.getString("student_number"));
            s.setMajorCourseId(rs.getInt("major_course_id"));
            s.setStatus(Student.Status.valueOf(rs.getString("status").toUpperCase()));
            return s;
        }, studentId);
    }
    public Student findByUserId(Long userId){
        String sql = "SELECT * FROM students WHERE user_id=?";

        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            Student s = new Student();
            s.setStudentId(rs.getInt("id"));
            s.setUserId(rs.getLong("user_id"));
            s.setStudentNumber(rs.getString("student_number"));
            s.setMajorCourseId(rs.getInt("major_course_id"));
            s.setStatus(Student.Status.valueOf(rs.getString("status").toUpperCase()));
            return s;
        }, userId);
    }
    public List<Student> findAll() {
        String sql = "SELECT * FROM students";

        return jdbc.query(sql, (rs, rowNum) -> {
            Student s = new Student();
            s.setStudentId(rs.getInt("id"));
            s.setUserId(rs.getLong("user_id"));
            s.setStudentNumber(rs.getString("student_number"));
            s.setMajorCourseId(rs.getInt("major_course_id"));
            s.setStatus(Student.Status.valueOf(rs.getString("status").toUpperCase()));
            return s;
        });
    }
}