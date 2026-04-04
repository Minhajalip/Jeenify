package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Long> {
    boolean existsByCourseIdAndTeacherId(int courseId, int teacherId);

    @Transactional
    void deleteByCourseIdAndTeacherId(int courseId, int teacherId);
}