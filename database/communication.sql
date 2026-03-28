-- 1. Attendance Claims
CREATE TABLE attendance_claims (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    session_id INT NOT NULL,
    reason TEXT,
    file_path VARCHAR(255),
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES attendance_sessions(id) ON DELETE CASCADE
);

-- Sample Data
INSERT INTO attendance_claims (student_id, session_id, reason, file_path, status)
VALUES
(1, 1, 'Medical leave', 'files/medical1.pdf', 'Pending'),
(2, 2, 'Family function', 'files/family1.pdf', 'Approved');


-- 2. Notifications
CREATE TABLE notifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Sample Data
INSERT INTO notifications (user_id, message)
VALUES
(1, 'Your attendance claim is under review'),
(2, 'New assignment uploaded');


-- 3. Resources
CREATE TABLE resources (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    title VARCHAR(255),
    file_path VARCHAR(255),
    uploaded_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (uploaded_by) REFERENCES users(id)
);

-- Sample Data
INSERT INTO resources (course_id, title, file_path, uploaded_by)
VALUES
(1, 'Lecture Notes 1', 'files/notes1.pdf', 1),
(2, 'Assignment Guide', 'files/guide.pdf', 2);


-- 4. Calendar Events
CREATE TABLE calendar_events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description TEXT,
    event_date DATE,
    created_by INT NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- Sample Data
INSERT INTO calendar_events (title, description, event_date, created_by)
VALUES
('Mid Exam', 'Mid semester exams start', '2026-04-10', 1),
('Project Submission', 'Final project deadline', '2026-05-01', 2);
