package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findByCourseId(int courseId);
    List<Timetable> findByTeacherId(int teacherId);
    List<Timetable> findByDayOfWeek(String dayOfWeek);
}