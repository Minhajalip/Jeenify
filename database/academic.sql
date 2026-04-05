CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department_id INT,
    major_course_id INT,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (major_course_id) REFERENCES courses(id)
);
CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);
CREATE TABLE course_teachers (
    course_id INT,
    teacher_id INT,
    PRIMARY KEY (course_id, teacher_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);
CREATE TABLE course_enrollments (
    course_id INT,
    student_id INT,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
INSERT INTO departments (name) VALUES
('Computer Science'),
('Electronics');
INSERT INTO teachers (name, department_id) VALUES
('Dr. Sharma', 1),
('Prof. Kumar', 2);
INSERT INTO courses (course_code, course_name, department_id) VALUES
('CS101', 'Data Structures', 1),
('EC201', 'Digital Circuits', 2);
INSERT INTO students (name, department_id, major_course_id) VALUES
('Amit', 1, 1),
('Neha', 1, 1),
('Rahul', 2, 2);
INSERT INTO course_teachers (course_id, teacher_id) VALUES
(1, 1),
(1, 2),
(2, 2);
INSERT INTO course_enrollments (course_id, student_id) VALUES
(1, 1),
(1, 2),
(2, 3);
