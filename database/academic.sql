CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE,
    course_name VARCHAR(100),
    department_id INT,
    main_teacher_id INT,
    
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (main_teacher_id) REFERENCES teachers(id)
);
CREATE TABLE course_enrollments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    student_id INT,
    
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
CREATE TABLE course_teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    teacher_id INT,
    
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);
INSERT INTO courses (course_code, course_name, department_id, main_teacher_id) VALUES
('CS101', 'Data Structures', 1, 1),
('EC201', 'Digital Circuits', 2, 2);
INSERT INTO course_enrollments (course_id, student_id) VALUES
(1, 1),
(1, 2),
(2, 3);
INSERT INTO course_enrollments (course_id, student_id) VALUES
(1, 1),
(1, 2),
(2, 3);
INSERT INTO course_teachers (course_id, teacher_id) VALUES
(1, 1),
(1, 2),
(2, 2);
