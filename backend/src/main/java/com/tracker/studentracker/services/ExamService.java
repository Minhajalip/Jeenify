package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Exam;
import com.tracker.studentracker.repository.CourseEnrollmentRepository;
import com.tracker.studentracker.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    // TEACHER/ADMIN - create exam
    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    // TEACHER/ADMIN - get all exams for a course
    public List<Exam> getExamsForCourse(int courseId) {
        return examRepository.findByCourseId(courseId);
    }

    // TEACHER/ADMIN - update exam
    public Exam updateExam(int examId, Exam updated) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setTitle(updated.getTitle());
        exam.setDescription(updated.getDescription());
        exam.setMaxMarks(updated.getMaxMarks());
        exam.setExamDate(updated.getExamDate());
        return examRepository.save(exam);
    }

    // TEACHER/ADMIN - delete exam
    public void deleteExam(int examId) {
        if (!examRepository.existsById(examId)) {
            throw new RuntimeException("Exam not found");
        }
        examRepository.deleteById(examId);
    }

    // STUDENT - get exams only for enrolled courses
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