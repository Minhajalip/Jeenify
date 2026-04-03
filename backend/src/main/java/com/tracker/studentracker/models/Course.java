<<<<<<< HEAD
package com.tracker.studentracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "main_teacher_id")
    private Long mainTeacherId;

    // Getters
    public Long getId() { return id; }

    public String getCourseCode() { return courseCode; }

    public String getCourseName() { return courseName; }

    public Long getDepartmentId() { return departmentId; }

    public Long getMainTeacherId() { return mainTeacherId; }

    // Setters
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public void setMainTeacherId(Long mainTeacherId) { this.mainTeacherId = mainTeacherId; }
}
