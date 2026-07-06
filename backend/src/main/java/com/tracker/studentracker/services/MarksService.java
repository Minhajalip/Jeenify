package com.tracker.studentracker.services;

import com.tracker.studentracker.models.AssignmentMark;
import com.tracker.studentracker.models.ExamMark;
import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {

    @Autowired
    private ExamMarkRepository examMarkRepository;

    @Autowired
    private AssignmentMarkRepository assignmentMarkRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private void checkTeacherOwnershipByExam(int examId, Long currentUserId) {
        int courseId = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"))
                .getCourseId();
        Teacher teacher = teacherRepository.findByUserId(currentUserId);
        if (teacher == null) throw new RuntimeException("Teacher profile not found");
        if (!courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacher.getId().intValue())) {
            throw new RuntimeException("Access denied: you are not assigned to this course");
        }
    }

    private void checkTeacherOwnershipByAssignment(int assignmentId, Long currentUserId) {
        int courseId = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"))
                .getCourseId();
        Teacher teacher = teacherRepository.findByUserId(currentUserId);
        if (teacher == null) throw new RuntimeException("Teacher profile not found");
        if (!courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacher.getId().intValue())) {
            throw new RuntimeException("Access denied: you are not assigned to this course");
        }
    }

    public ExamMark enterExamMarks(ExamMark mark, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByExam(mark.getExamId(), currentUserId);
        }
        if (examMarkRepository.existsByStudentIdAndExamId(mark.getStudentId(), mark.getExamId())) {
            throw new RuntimeException("Marks already entered for this student and exam");
        }
        return examMarkRepository.save(mark);
    }

    public ExamMark updateExamMarks(int studentId, int examId, int marksObtained, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByExam(examId, currentUserId);
        }
        ExamMark mark = examMarkRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new RuntimeException("Marks not found for this student and exam"));
        mark.setMarksObtained(marksObtained);
        return examMarkRepository.save(mark);
    }

    public List<ExamMark> getMarksByExam(int examId, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByExam(examId, currentUserId);
        }
        return examMarkRepository.findByExamId(examId);
    }

    public List<ExamMark> getExamMarksByStudent(int studentId) {
        return examMarkRepository.findByStudentId(studentId);
    }

    public AssignmentMark enterAssignmentMarks(AssignmentMark mark, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByAssignment(mark.getAssignmentId(), currentUserId);
        }
        if (assignmentMarkRepository.existsByStudentIdAndAssignmentId(mark.getStudentId(), mark.getAssignmentId())) {
            throw new RuntimeException("Marks already entered for this student and assignment");
        }
        return assignmentMarkRepository.save(mark);
    }

    public AssignmentMark updateAssignmentMarks(int studentId, int assignmentId, int marksObtained, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByAssignment(assignmentId, currentUserId);
        }
        AssignmentMark mark = assignmentMarkRepository.findByStudentIdAndAssignmentId(studentId, assignmentId)
                .orElseThrow(() -> new RuntimeException("Marks not found for this student and assignment"));
        mark.setMarksObtained(marksObtained);
        return assignmentMarkRepository.save(mark);
    }

    public List<AssignmentMark> getMarksByAssignment(int assignmentId, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnershipByAssignment(assignmentId, currentUserId);
        }
        return assignmentMarkRepository.findByAssignmentId(assignmentId);
    }

    public List<AssignmentMark> getAssignmentMarksByStudent(int studentId) {
        return assignmentMarkRepository.findByStudentId(studentId);
    }
}