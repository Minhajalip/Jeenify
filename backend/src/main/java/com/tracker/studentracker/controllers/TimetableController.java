package com.tracker.studentracker.controllers;

import com.tracker.studentracker.models.Timetable;
import com.tracker.studentracker.services.TimetableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping
    public List<Timetable> getTimetable() {
        return timetableService.getAllTimetables();
    }
}
