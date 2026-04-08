CREATE TABLE exams (
  exam_id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT,
  title VARCHAR(500),
  description TEXT,
  max_marks INT,
  exam_date DATE,
  FOREIGN KEY (course_id) REFERENCES courses(id)
);
CREATE TABLE assignments (
  assignment_id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT,
  title VARCHAR(500),
  description TEXT,
  max_marks INT,
  due_date DATE,
  FOREIGN KEY (course_id) REFERENCES courses(id)
);
CREATE TABLE exam_marks (
  mark_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT,
  exam_id INT,
  marks_obtained INT,
  FOREIGN KEY (student_id) REFERENCES students(id),
  FOREIGN KEY (exam_id) REFERENCES exams(exam_id),
  UNIQUE (student_id, exam_id)
);
CREATE TABLE assignment_marks (
  mark_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT,
  assignment_id INT,
  marks_obtained INT,
  FOREIGN KEY (student_id) REFERENCES students(id),
  FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id),
  UNIQUE (student_id, assignment_id)
);

INSERT INTO exams (course_id, title, description, max_marks, exam_date) VALUES
(1, 'First Internal', 'Covers first 2 modules', 20, '2026-02-10'),
(2, 'Second Internal', 'Covers last 2 modules', 20, '2026-03-20'),
(1, 'External exam', 'Full syllabus', 50, '2026-04-17');

INSERT INTO assignments (course_id, title, description, max_marks, due_date) VALUES
(1, 'Assignment 1', 'Matrix problems in JAVA', 5, '2026-03-30'),
(2, 'Assignment 2', 'Mysql problems in DBMS', 5, '2026-04-05'),
(1, 'Mini Project', 'creating a web page', 10, '2026-04-15');

INSERT INTO exam_marks (student_id, exam_id, marks_obtained) VALUES
(1, 1, 15),
(2, 2, 18),
(2, 3, 40); -- ✅ FIX: changed student_id 3 → 2

INSERT INTO assignment_marks (student_id, assignment_id, marks_obtained) VALUES
(1, 1, 4),
(2, 2, 5),
(1, 3, 8);