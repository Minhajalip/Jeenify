package com.tracker.studentracker.controllers;

import com.tracker.studentracker.config.AuthHelper;
import com.tracker.studentracker.models.CalendarEvent;
import com.tracker.studentracker.services.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "*")
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @Autowired
    private AuthHelper authHelper;

    // Get all events
    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        try {
            return ResponseEntity.ok(calendarEventService.getAllEvents());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get events for a specific date
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getEventsByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            return ResponseEntity.ok(calendarEventService.getEventsByDate(localDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get events between two dates
    @GetMapping("/range")
    public ResponseEntity<?> getEventsBetween(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            return ResponseEntity.ok(calendarEventService.getEventsBetween(start, end));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ADMIN - create an event
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CalendarEvent event) {
        try {
            Long userId = authHelper.getCurrentUserId();
            CalendarEvent created = calendarEventService.createEvent(event, userId.intValue());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ADMIN - update an event
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody CalendarEvent event) {
        try {
            CalendarEvent updated = calendarEventService.updateEvent(id, event);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ADMIN - delete an event
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id) {
        try {
            calendarEventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}