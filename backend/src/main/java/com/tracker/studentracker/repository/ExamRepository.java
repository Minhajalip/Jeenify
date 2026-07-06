package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    List<Exam> findByCourseId(int courseId);
}