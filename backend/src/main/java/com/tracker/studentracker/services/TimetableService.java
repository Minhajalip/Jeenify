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
}
