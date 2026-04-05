package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {
    List<CalendarEvent> findByEventDate(LocalDate eventDate);
    List<CalendarEvent> findByEventDateBetween(LocalDate startDate, LocalDate endDate);
}