package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.AssignmentMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentMarkRepository extends JpaRepository<AssignmentMark, Integer> {
    List<AssignmentMark> findByStudentId(int studentId);
    List<AssignmentMark> findByAssignmentId(int assignmentId);
    Optional<AssignmentMark> findByStudentIdAndAssignmentId(int studentId, int assignmentId);
    boolean existsByStudentIdAndAssignmentId(int studentId, int assignmentId);
}