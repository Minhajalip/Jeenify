package com.tracker.studentracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "timetable")
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "period")
    private int period;

    @Column(name = "room")
    private String room;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // keeping setTimetableId so TimetableController doesn't break
    public void setTimetableId(Long id) { this.id = id; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public int getPeriod() { return period; }
    public void setPeriod(int period) { this.period = period; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}