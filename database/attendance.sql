CREATE TABLE timetable (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    teacher_id INT,
    day_of_week VARCHAR(10),
    period INT,
    room VARCHAR(20),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

INSERT INTO timetable (course_id, teacher_id, day_of_week, period, room)
VALUES (1, 1, 'Monday', 1, 'A101'),
       (2, 2, 'Tuesday', 3, 'B202');


CREATE TABLE attendance_sessions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    teacher_id INT,
    session_date DATE,
    period INT,
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

INSERT INTO attendance_sessions (course_id, teacher_id, session_date, period)
VALUES (1, 1, '2026-03-25', 1),
       (2, 2, '2026-03-25', 3);


CREATE TABLE attendance_records (
    id INT PRIMARY KEY AUTO_INCREMENT,
    session_id INT,
    student_id INT,
    status VARCHAR(10),
    FOREIGN KEY (session_id) REFERENCES attendance_sessions(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO attendance_records (session_id, student_id, status)
VALUES (1, 1, 'Present'),
       (1, 2, 'Absent');