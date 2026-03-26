package com.tracker.studentracker.services;
import com.tracker.studentracker.models.Timetable;
import com.tracker.studentracker.repository.TimetableRepository;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;

    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    public List<Timetable> getAllTimetables() {
        return timetableRepository.getAllTimetables();
    }
    public void addTimetable(Timetable t) {
        if (t.getCourseId() == null) throw new RuntimeException("courseId is required");
        if (t.getDayOfWeek() == null || t.getDayOfWeek().isEmpty()) throw new RuntimeException("dayOfWeek is required");
        if (t.getPeriodNumber() == null) throw new RuntimeException("periodNumber is required");
        timetableRepository.addTimetable(t);
    }
    public boolean updateTimetable(Timetable t) {
        if (t.getTimetableId() == null) throw new RuntimeException("timetableId is required");
        if (t.getCourseId() == null) throw new RuntimeException("courseId is required");
        if (t.getDayOfWeek() == null || t.getDayOfWeek().isEmpty()) throw new RuntimeException("dayOfWeek is required");
        return timetableRepository.updateTimetable(t) > 0;
    }
    public void deleteTimetable(Long id) {
        timetableRepository.deleteTimetable(id);
    }
    public Timetable getTimetableById(Long id) {
        return timetableRepository.getTimetableById(id);
    }
}
