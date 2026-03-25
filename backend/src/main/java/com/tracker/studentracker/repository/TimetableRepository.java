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

            t.setCourseId(rs.getObject("course_id") != null ? rs.getLong("course_id") : null);
            t.setDayOfWeek(rs.getString("day_of_week"));
            t.setPeriodNumber(rs.getObject("period_number") != null ? rs.getInt("period_number") : null);
            t.setSubject(rs.getString("subject"));
            t.setTeacherId(rs.getObject("teacher_id") != null ? rs.getLong("teacher_id") : null);

            return t;
        });
    }
    public void addTimetable(Timetable t) {
        String sql = "INSERT INTO timetable (course_id, day_of_week, period_number, subject, teacher_id) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                t.getCourseId(),
                t.getDayOfWeek(),
                t.getPeriodNumber(),
                t.getSubject(),
                t.getTeacherId()
        );
    }
    public int updateTimetable(Timetable t) {
        String sql = "UPDATE timetable SET course_id=?, day_of_week=?, period_number=?, subject=?, teacher_id=? WHERE timetable_id=?";

        return jdbcTemplate.update(sql,
                t.getCourseId(),
                t.getDayOfWeek(),
                t.getPeriodNumber(),
                t.getSubject(),
                t.getTeacherId(),
                t.getTimetableId()
        );
    }
    public void deleteTimetable(Long id) {
        String sql = "DELETE FROM timetable WHERE timetable_id=?";
        jdbcTemplate.update(sql, id);
    }
    public Timetable getTimetableById(Long id) {
        String sql = "SELECT * FROM timetable WHERE timetable_id = ?";

        List<Timetable> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Timetable t = new Timetable();
            t.setTimetableId(rs.getLong("timetable_id"));
            t.setCourseId(rs.getObject("course_id") != null ? rs.getLong("course_id") : null);
            t.setDayOfWeek(rs.getString("day_of_week"));
            t.setPeriodNumber(rs.getObject("period_number") != null ? rs.getInt("period_number") : null);
            t.setSubject(rs.getString("subject"));
            t.setTeacherId(rs.getObject("teacher_id") != null ? rs.getLong("teacher_id") : null);
            return t;
        }, id);

        return result.isEmpty() ? null : result.get(0);
    }
}
