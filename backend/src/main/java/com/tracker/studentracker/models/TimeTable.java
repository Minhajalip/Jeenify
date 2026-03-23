package com.tracker.studentracker.models;

public class TimeTable {
    private Long timetableId;
    private Long courseId;
    private String dayOfWeek;
    private Integer periodNumber;
    private String subject;
    private Long teacherId;

    public Long getTimetableId() { return timetableId; }
    public void setTimetableId(Long timetableId) { this.timetableId = timetableId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Integer getPeriodNumber() { return periodNumber; }
    public void setPeriodNumber(Integer periodNumber) { this.periodNumber = periodNumber; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}
