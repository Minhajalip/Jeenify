package com.tracker.studentracker.repository;
import com.tracker.studentracker.models.Timetable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimetableRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimetableRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Timetable> getAllTimetables() {
        String sql = "SELECT * FROM timetable";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Timetable t = new Timetable();
            t.setTimetableId(rs.getLong("timetable_id"));
            t.setCourseId(rs.getLong("course_id"));
            t.setDayOfWeek(rs.getString("day_of_week"));
            t.setPeriodNumber(rs.getInt("period_number"));
            t.setSubject(rs.getString("subject"));
            t.setTeacherId(rs.getLong("teacher_id"));
            return t;
        });
    }
}
