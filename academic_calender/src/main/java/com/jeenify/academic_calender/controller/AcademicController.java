package com.jeenify.academic_calender.controller;

import com.jeenify.academic_calender.model.AcademicEvent;
import com.jeenify.academic_calender.repository.AcademicEventRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AcademicController {

    private final AcademicEventRepository repo;

    public AcademicController(AcademicEventRepository repo) {
        this.repo = repo;
    }

    // POST: Add event
    @PostMapping("/event")
    public AcademicEvent addEvent(@RequestBody AcademicEvent event) {
        return repo.save(event);
    }

    // GET: Get all events
    @GetMapping("/events")
    public List<AcademicEvent> getEvents() {
        return repo.findAll();
    }
}