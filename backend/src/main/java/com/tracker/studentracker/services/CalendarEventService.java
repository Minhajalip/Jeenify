package com.tracker.studentracker.services;

import com.tracker.studentracker.models.CalendarEvent;
import com.tracker.studentracker.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    // Get all events
    public List<CalendarEvent> getAllEvents() {
        return calendarEventRepository.findAll();
    }

    // Get events for a specific date
    public List<CalendarEvent> getEventsByDate(LocalDate date) {
        return calendarEventRepository.findByEventDate(date);
    }

    // Get events between two dates
    public List<CalendarEvent> getEventsBetween(LocalDate startDate, LocalDate endDate) {
        return calendarEventRepository.findByEventDateBetween(startDate, endDate);
    }

    // Create an event
    public CalendarEvent createEvent(CalendarEvent event, int createdBy) {
        event.setCreatedBy(createdBy);
        return calendarEventRepository.save(event);
    }

    // Update an event
    public CalendarEvent updateEvent(int id, CalendarEvent updated) {
        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(updated.getTitle());
        event.setDescription(updated.getDescription());
        event.setEventDate(updated.getEventDate());
        return calendarEventRepository.save(event);
    }

    // Delete an event
    public void deleteEvent(int id) {
        if (!calendarEventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        calendarEventRepository.deleteById(id);
    }
}