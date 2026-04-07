CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255),
    role ENUM('ADMIN','TEACHER','STUDENT'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    student_number VARCHAR(50) UNIQUE,
    major_course_id INT,
    status ENUM('pending','approved'),

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    department_id INT,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (name, email, password_hash, role) VALUES
('Admin User', 'admin@college.com', 'hash123', 'ADMIN'),
('John Student', 'john@student.com', 'hash123', 'STUDENT'),
('Aisha Student', 'aisha@student.com', 'hash123', 'STUDENT'),
('Dr Smith', 'smith@college.com', 'hash123', 'TEACHER'),
('Prof Kumar', 'kumar@college.com', 'hash123', 'TEACHER');

INSERT INTO students (user_id, student_number, major_course_id, status) VALUES
(2, 'STU001', 1, 'approved'),
(3, 'STU002', 1, 'pending');

INSERT INTO teachers (user_id, department_id) VALUES
(4, 1),
(5, 1);
