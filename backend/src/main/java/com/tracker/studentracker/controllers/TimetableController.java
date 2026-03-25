package com.tracker.studentracker.controllers;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<String> addTimetable(@RequestBody Timetable timetable) {
        try {
            timetableService.addTimetable(timetable);
            return ResponseEntity.ok("Timetable added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTimetable(@PathVariable Long id, @RequestBody Timetable timetable) {
        try {
            timetable.setTimetableId(id);
            boolean updated = timetableService.updateTimetable(timetable);
            if (!updated) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 for missing fields
        }
    }
    @DeleteMapping("/{id}")
    public String deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return "Deleted successfully";
    }
    @GetMapping("/{id}")
    public ResponseEntity<Timetable> getTimetableById(@PathVariable Long id) {
        Timetable timetable = timetableService.getTimetableById(id);

        if (timetable == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity.ok(timetable); // 200
    }
}
