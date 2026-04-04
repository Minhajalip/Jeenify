package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Long> {
}