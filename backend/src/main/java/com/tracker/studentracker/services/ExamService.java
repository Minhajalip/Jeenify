package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Exam;
import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.CourseEnrollmentRepository;
import com.tracker.studentracker.repository.CourseTeacherRepository;
import com.tracker.studentracker.repository.ExamRepository;
import com.tracker.studentracker.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private void checkTeacherOwnership(int courseId, Long currentUserId) {
        Teacher teacher = teacherRepository.findByUserId(currentUserId);
        if (teacher == null) throw new RuntimeException("Teacher profile not found");
        if (!courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacher.getId().intValue())) {
            throw new RuntimeException("Access denied: you are not assigned to this course");
        }
    }

    public Exam createExam(Exam exam, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(exam.getCourseId(), currentUserId);
        }
        return examRepository.save(exam);
    }

    public List<Exam> getExamsForCourse(int courseId, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(courseId, currentUserId);
        }
        return examRepository.findByCourseId(courseId);
    }

    public Exam updateExam(int examId, Exam updated, String role, Long currentUserId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(exam.getCourseId(), currentUserId);
        }
        exam.setTitle(updated.getTitle());
        exam.setDescription(updated.getDescription());
        exam.setMaxMarks(updated.getMaxMarks());
        exam.setExamDate(updated.getExamDate());
        return examRepository.save(exam);
    }

    public void deleteExam(int examId, String role, Long currentUserId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(exam.getCourseId(), currentUserId);
        }
        examRepository.deleteById(examId);
    }

    public List<Exam> getExamsForStudent(int studentId) {
        List<Integer> enrolledCourseIds = enrollmentRepository.getEnrolledCourseIds(studentId);
        if (enrolledCourseIds.isEmpty()) {
            throw new RuntimeException("Student is not enrolled in any courses");
        }
        return examRepository.findAll().stream()
                .filter(exam -> enrolledCourseIds.contains(exam.getCourseId()))
                .toList();
    }
}