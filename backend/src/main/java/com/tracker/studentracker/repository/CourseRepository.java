package com.tracker.studentracker.repository;


import com.tracker.studentracker.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}