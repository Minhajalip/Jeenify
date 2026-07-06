-- =========================
-- TIMETABLE TABLE
-- =========================
CREATE TABLE timetable (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,
    period INT NOT NULL,
    room VARCHAR(20),

    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

-- Sample Data
INSERT INTO timetable (course_id, teacher_id, day_of_week, period, room)
VALUES 
(1, 1, 'Monday', 1, 'A101'),
(2, 2, 'Tuesday', 3, 'B202');


-- =========================
-- ATTENDANCE SESSIONS
-- =========================
CREATE TABLE attendance_sessions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    session_date DATE NOT NULL,
    period INT NOT NULL,

    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

-- Sample Data
INSERT INTO attendance_sessions (course_id, teacher_id, session_date, period)
VALUES 
(1, 1, '2026-03-25', 1),
(2, 2, '2026-03-25', 3);


-- =========================
-- ATTENDANCE RECORDS
-- =========================
CREATE TABLE attendance_records (
    id INT PRIMARY KEY AUTO_INCREMENT,
    session_id INT NOT NULL,
    student_id INT NOT NULL,
    status ENUM('Present', 'Absent', 'Late') NOT NULL,

    FOREIGN KEY (session_id) REFERENCES attendance_sessions(id),
    FOREIGN KEY (student_id) REFERENCES students(id),

    UNIQUE (session_id, student_id)  -- prevents duplicate attendance
);

-- Sample Data
INSERT INTO attendance_records (session_id, student_id, status)
VALUES 
(1, 1, 'Present'),
(1, 2, 'Absent');