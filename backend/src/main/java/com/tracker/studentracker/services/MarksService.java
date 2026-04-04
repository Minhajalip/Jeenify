package com.tracker.studentracker.services;

import com.tracker.studentracker.models.AssignmentMark;
import com.tracker.studentracker.models.ExamMark;
import com.tracker.studentracker.repository.AssignmentMarkRepository;
import com.tracker.studentracker.repository.ExamMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {

    @Autowired
    private ExamMarkRepository examMarkRepository;

    @Autowired
    private AssignmentMarkRepository assignmentMarkRepository;

    // TEACHER/ADMIN - enter exam marks
    public ExamMark enterExamMarks(ExamMark mark) {
        if (examMarkRepository.existsByStudentIdAndExamId(mark.getStudentId(), mark.getExamId())) {
            throw new RuntimeException("Marks already entered for this student and exam");
        }
        return examMarkRepository.save(mark);
    }

    // TEACHER/ADMIN - update exam marks
    public ExamMark updateExamMarks(int studentId, int examId, int marksObtained) {
        ExamMark mark = examMarkRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new RuntimeException("Marks not found for this student and exam"));
        mark.setMarksObtained(marksObtained);
        return examMarkRepository.save(mark);
    }

    // TEACHER/ADMIN - get all marks for an exam
    public List<ExamMark> getMarksByExam(int examId) {
        return examMarkRepository.findByExamId(examId);
    }

    // STUDENT - get own exam marks
    public List<ExamMark> getExamMarksByStudent(int studentId) {
        return examMarkRepository.findByStudentId(studentId);
    }

    // TEACHER/ADMIN - enter assignment marks
    public AssignmentMark enterAssignmentMarks(AssignmentMark mark) {
        if (assignmentMarkRepository.existsByStudentIdAndAssignmentId(mark.getStudentId(), mark.getAssignmentId())) {
            throw new RuntimeException("Marks already entered for this student and assignment");
        }
        return assignmentMarkRepository.save(mark);
    }

    // TEACHER/ADMIN - update assignment marks
    public AssignmentMark updateAssignmentMarks(int studentId, int assignmentId, int marksObtained) {
        AssignmentMark mark = assignmentMarkRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(() -> new RuntimeException("Marks not found for this student and assignment"));
        mark.setMarksObtained(marksObtained);
        return assignmentMarkRepository.save(mark);
    }

    // TEACHER/ADMIN - get all marks for an assignment
    public List<AssignmentMark> getMarksByAssignment(int assignmentId) {
        return assignmentMarkRepository.findByAssignmentId(assignmentId);
    }

    // STUDENT - get own assignment marks
    public List<AssignmentMark> getAssignmentMarksByStudent(int studentId) {
        return assignmentMarkRepository.findByStudentId(studentId);
    }
}