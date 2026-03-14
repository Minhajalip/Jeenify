
# Database Schema

Jeenify – Student Attendance & Assessment Management System

This document defines the database structure for the Jeenify system.

## The schema supports:

- User management
- Student and teacher records
- Course enrollment
- Timetable scheduling
- Attendance tracking
- Attendance claims
- Exams and assignments
- Academic calendar

All tables use primary keys and foreign keys to maintain relationships.

---

## 1. Users Table

Stores authentication and role information.
```bash
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','TEACHER','STUDENT') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
---

## 2. Students Table

Stores student-specific information.
```bash
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    user_id INT UNIQUE,
    student_number VARCHAR(50) UNIQUE,
    major_course_id INT,
    approval_status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```
---

## 3. Teachers Table

Stores teacher-specific information.
```bash
CREATE TABLE teachers (
    teacher_id INT PRIMARY KEY,
    user_id INT UNIQUE,
    department VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```
---

## 4. Courses Table

Stores course details.
```bash
CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(50) UNIQUE,
    description TEXT
);
```
---

## 5. Course Enrollments

Tracks students enrolled in courses.
```bash
CREATE TABLE course_enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
```
---

## 6. Course Teachers

Maps teachers to courses.
```bash
CREATE TABLE course_teachers (
    course_teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    teacher_id INT,
    is_main_teacher BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id)
);
```
---

## 7. Timetable Table

Stores weekly course schedule.
```bash
CREATE TABLE timetable (
    timetable_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    day_of_week ENUM('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY'),
    period_number INT,
    subject VARCHAR(100),
    teacher_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id)
);
```
---

## 8. Attendance Sessions

Stores class sessions where attendance is taken.
```bash
CREATE TABLE attendance_sessions (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    session_date DATE,
    period_number INT,
    created_by INT,
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    FOREIGN KEY (created_by) REFERENCES teachers(teacher_id)
);
```
---

## 9. Attendance Records

Stores individual student attendance.
```bash
CREATE TABLE attendance_records (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT,
    student_id INT,
    status ENUM('PRESENT','ABSENT') NOT NULL,
    FOREIGN KEY (session_id) REFERENCES attendance_sessions(session_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);
```
---

## 10. Attendance Claims

Students can submit claims for incorrect attendance.
```bash
CREATE TABLE attendance_claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    attendance_id INT,
    student_id INT,
    reason TEXT,
    status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (attendance_id) REFERENCES attendance_records(attendance_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);
```
---

## 11. Exams Table

Stores exam details.
```bash
CREATE TABLE exams (
    exam_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    title VARCHAR(100),
    description TEXT,
    max_marks INT,
    exam_date DATE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
```
---

## 12. Assignments Table

Stores assignment details.
```bash
CREATE TABLE assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    title VARCHAR(100),
    description TEXT,
    max_marks INT,
    due_date DATE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
```
---

## 13. Marks Table

Stores marks for exams and assignments.
```bash
CREATE TABLE marks (
    mark_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    exam_id INT NULL,
    assignment_id INT NULL,
    marks_obtained INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id),
    FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id)
);
```
---

## 14. Academic Calendar

Stores academic events.
```bash
CREATE TABLE academic_calendar (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    event_date DATE,
    event_type ENUM('SEMESTER','EXAM','HOLIDAY','DEADLINE')
);
```
---

## Database Relationships Overview

### Key relationships in the system:

- "users" → "students"
- "users" → "teachers"
- "students" → "course_enrollments"
- "courses" → "course_enrollments"
- "courses" → "course_teachers"
- "courses" → "timetable"
- "courses" → "attendance_sessions"
- "attendance_sessions" → "attendance_records"
- "attendance_records" → "attendance_claims"
- "courses" → "exams"
- "courses" → "assignments"
- "exams / assignments" → "marks"
- "academic_calendar" stores academic events

---

### Database Implementation Notes

- Use MySQL InnoDB engine for foreign key support.
- Add indexes on foreign keys for performance.
- Passwords must be securely hashed before storing.
- Only approved students should be allowed to access the system.
