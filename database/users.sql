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
    major_course_id INT, -- ✅ FK constraint moved to academic.sql
    status ENUM('pending','approved'),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    department_id INT, -- ✅ FK constraint moved to academic.sql
    FOREIGN KEY (user_id) REFERENCES users(id)
);
INSERT INTO users (name, email, password_hash, role) VALUES
('Admin User', 'admin@college.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN'),
('John Student', 'john@student.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'STUDENT'),
('Aisha Student', 'aisha@student.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'STUDENT'),
('Dr Smith', 'smith@college.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TEACHER'),
('Prof Kumar', 'kumar@college.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TEACHER');
INSERT INTO students (user_id, student_number, major_course_id, status) VALUES
(2, 'STU001', 1, 'approved'),
(3, 'STU002', 1, 'pending');
INSERT INTO teachers (user_id, department_id) VALUES
(4, 1),
(5, 1);