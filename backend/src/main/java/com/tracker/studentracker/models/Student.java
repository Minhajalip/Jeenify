package com.tracker.studentracker.models;

public class Student {
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    private int studentId;
    private Long userId;
    private String studentNumber;
    private int majorCourseId;
    private Status status;

    public int getStudentId() { return this.studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public Long getUserId() { return this.userId; }   // ✅ was int, now Long
    public void setUserId(Long userId) { this.userId = userId; }

    public String getStudentNumber() { return this.studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public int getMajorCourseId() { return this.majorCourseId; }
    public void setMajorCourseId(int majorCourseId) { this.majorCourseId = majorCourseId; }

    public Status getStatus() { return this.status; }
    public void setStatus(Status status) { this.status = status; }
}