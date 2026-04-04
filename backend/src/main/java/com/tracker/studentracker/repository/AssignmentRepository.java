package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByCourseId(int courseId);
}