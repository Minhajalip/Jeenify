package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.ExamMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamMarkRepository extends JpaRepository<ExamMark, Integer> {
    List<ExamMark> findByStudentId(int studentId);
    List<ExamMark> findByExamId(int examId);
    Optional<ExamMark> findByStudentIdAndExamId(int studentId, int examId);
    boolean existsByStudentIdAndExamId(int studentId, int examId);
}