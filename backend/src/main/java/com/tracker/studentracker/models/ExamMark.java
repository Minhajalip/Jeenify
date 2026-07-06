package com.tracker.studentracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "exam_marks")
public class ExamMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_id")
    private int markId;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "exam_id")
    private int examId;

    @Column(name = "marks_obtained")
    private int marksObtained;

    public int getMarkId() { return markId; }
    public void setMarkId(int markId) { this.markId = markId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getMarksObtained() { return marksObtained; }
    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }
}