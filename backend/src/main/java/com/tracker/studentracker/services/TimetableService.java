package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Timetable;
import com.tracker.studentracker.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    public Timetable getTimetableById(Long id) {
        return timetableRepository.findById(id).orElse(null);
    }

    public List<Timetable> getTimetableByCourse(int courseId) {
        return timetableRepository.findByCourseId(courseId);
    }

    public List<Timetable> getTimetableByTeacher(int teacherId) {
        return timetableRepository.findByTeacherId(teacherId);
    }

    public List<Timetable> getTimetableByDay(String dayOfWeek) {
        return timetableRepository.findByDayOfWeek(dayOfWeek);
    }

    public Timetable addTimetable(Timetable timetable) {
        if (timetable.getCourseId() == 0) throw new RuntimeException("courseId is required");
        if (timetable.getDayOfWeek() == null || timetable.getDayOfWeek().isEmpty()) throw new RuntimeException("dayOfWeek is required");
        if (timetable.getPeriod() == 0) throw new RuntimeException("period is required");
        return timetableRepository.save(timetable);
    }

    public boolean updateTimetable(Timetable timetable) {
        if (!timetableRepository.existsById(timetable.getId())) return false;
        timetableRepository.save(timetable);
        return true;
    }

    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }
}