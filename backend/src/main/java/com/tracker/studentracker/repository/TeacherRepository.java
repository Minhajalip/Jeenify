package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByUserId(Long userId);
}