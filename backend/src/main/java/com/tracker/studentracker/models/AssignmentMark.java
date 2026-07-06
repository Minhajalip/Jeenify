package com.tracker.studentracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "assignment_marks")
public class AssignmentMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_id")
    private int markId;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "assignment_id")
    private int assignmentId;

    @Column(name = "marks_obtained")
    private int marksObtained;

    public int getMarkId() { return markId; }
    public void setMarkId(int markId) { this.markId = markId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public int getMarksObtained() { return marksObtained; }
    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }
}