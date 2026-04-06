-- =========================
-- 1. USERS
-- =========================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','TEACHER','STUDENT') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- 2. DEPARTMENTS
-- =========================
CREATE TABLE departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- 3. COURSES
-- =========================
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- =========================
-- 4. STUDENTS
-- =========================
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    student_number VARCHAR(50) UNIQUE,
    department_id INT,
    major_course_id INT,
    status ENUM('pending','approved'),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (major_course_id) REFERENCES courses(id)
);

-- =========================
-- 5. TEACHERS
-- =========================
CREATE TABLE teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    department_id INT,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- =========================
-- 6. COURSE - TEACHERS
-- =========================
CREATE TABLE course_teachers (
    course_id INT,
    teacher_id INT,
    PRIMARY KEY (course_id, teacher_id),

    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

-- =========================
-- 7. COURSE ENROLLMENTS
-- =========================
CREATE TABLE course_enrollments (
    course_id INT,
    student_id INT,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

-- =========================
-- 8. EXAMS
-- =========================
CREATE TABLE exams (
    exam_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    title VARCHAR(255),
    description TEXT,
    max_marks INT,
    exam_date DATE,

    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- =========================
-- 9. ASSIGNMENTS
-- =========================
CREATE TABLE assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    title VARCHAR(255),
    description TEXT,
    max_marks INT,
    due_date DATE,

    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- =========================
-- 10. EXAM MARKS
-- =========================
CREATE TABLE exam_marks (
    mark_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    exam_id INT,
    marks_obtained INT,

    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id),
    UNIQUE (student_id, exam_id)
);

-- =========================
-- 11. ASSIGNMENT MARKS
-- =========================
CREATE TABLE assignment_marks (
    mark_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    assignment_id INT,
    marks_obtained INT,

    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id),
    UNIQUE (student_id, assignment_id)
);

-- =========================
-- 12. TIMETABLE
-- =========================
CREATE TABLE timetable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    teacher_id INT,
    day_of_week VARCHAR(10),
    period INT,
    room VARCHAR(20),

    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

-- =========================
-- 13. ATTENDANCE
-- =========================
CREATE TABLE attendance_sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    teacher_id INT,
    session_date DATE,
    period INT,

    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

CREATE TABLE attendance_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT,
    student_id INT,
    status ENUM('Present','Absent','Late'),

    FOREIGN KEY (session_id) REFERENCES attendance_sessions(id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    UNIQUE (session_id, student_id)
);

-- =========================
-- 14. ATTENDANCE CLAIMS
-- =========================
CREATE TABLE attendance_claims (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    session_id INT,
    reason TEXT,
    file_path VARCHAR(255),
    status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending',

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES attendance_sessions(id) ON DELETE CASCADE
);

-- =========================
-- 15. NOTIFICATIONS
-- =========================
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =========================
-- 16. RESOURCES
-- =========================
CREATE TABLE resources (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,
    title VARCHAR(255),
    file_path VARCHAR(255),
    uploaded_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (uploaded_by) REFERENCES users(id)
);

-- =========================
-- 17. CALENDAR EVENTS
-- =========================
CREATE TABLE calendar_events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    event_date DATE,
    created_by INT,

    FOREIGN KEY (created_by) REFERENCES users(id)
);
