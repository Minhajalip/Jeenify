package com.tracker.studentracker.dto;

public class StudentRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String studentNumber;
    private int majorCourseId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public int getMajorCourseId() { return majorCourseId; }
    public void setMajorCourseId(int majorCourseId) { this.majorCourseId = majorCourseId; }
}