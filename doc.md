# 📘 Jeenify – Student Management System (Backend Documentation)

---

## 1. 📌 Project Overview

Jeenify is a **Student Management System backend** designed to handle academic and administrative operations such as:

* Student authentication
* Attendance management
* Course management
* Academic calendar
* Timetable scheduling

The system is developed using **Spring Boot (Java)** and follows a **RESTful API architecture**.

---

## 2. 🎯 Key Features

### ✅ Implemented Modules

* Authentication System (Login/Register)
* Student Management
* Attendance Management
* Course Management
* Academic Calendar
* Timetable API
* Database Integration

---

## 3. 🛠️ Tech Stack

* Java (Spring Boot)
* Spring Security
* JWT Authentication
* Maven
* REST APIs
* Relational Database (MySQL/PostgreSQL)

---

## 4. 📂 Project Modules & Responsibilities

### 🔐 1. Authentication Module

Handles user login, registration, and security.

#### Functions Used:

* `registerUser(RegisterRequest request)`

  * Registers a new user
* `loginUser(LoginRequest request)`

  * Authenticates user credentials
* `generateToken(User user)`

  * Generates JWT token
* `validateToken(String token)`

  * Validates JWT token
* `loadUserByUsername(String username)`

  * Loads user from database

---

### 👤 2. Student Management Module

#### Functions:

* `createStudent(Student student)`

  * Adds new student
* `getStudentById(Long id)`

  * Fetch student details
* `getAllStudents()`

  * Retrieve all students
* `updateStudent(Long id, Student student)`

  * Update student info
* `deleteStudent(Long id)`

  * Remove student

---

### 📅 3. Academic Calendar Module

#### Functions:

* `addEvent(AcademicEvent event)`

  * Create new event
* `getAllEvents()`

  * Retrieve all events
* `getEventById(Long id)`

  * Fetch specific event
* `updateEvent(Long id, AcademicEvent event)`

  * Update event
* `deleteEvent(Long id)`

  * Delete event

---

### 📚 4. Course Management Module

#### Functions:

* `createCourse(Course course)`

  * Add new course
* `getAllCourses()`

  * List all courses
* `getCourseById(Long id)`

  * Fetch course details
* `updateCourse(Long id, Course course)`

  * Update course
* `deleteCourse(Long id)`

  * Delete course

---

### 🕒 5. Timetable Module

#### Functions:

* `createTimetable(Timetable timetable)`

  * Add timetable entry
* `getTimetableByClass(String className)`

  * Retrieve timetable
* `updateTimetable(Long id, Timetable timetable)`

  * Update schedule
* `deleteTimetable(Long id)`

  * Delete timetable entry

---

### 🧾 6. Attendance Management Module

#### Functions:

* `markAttendance(Attendance attendance)`

  * Mark student attendance
* `getAttendanceByStudent(Long studentId)`

  * Fetch attendance records
* `getAttendanceByDate(LocalDate date)`

  * Retrieve attendance by date
* `updateAttendance(Long id, Attendance attendance)`

  * Modify attendance
* `deleteAttendance(Long id)`

  * Remove attendance record

---

## 5. 🔗 API Endpoints Overview

### Authentication

* POST `/auth/register`
* POST `/auth/login`

### Students

* GET `/students`
* GET `/students/{id}`
* POST `/students`
* PUT `/students/{id}`
* DELETE `/students/{id}`

### Attendance

* POST `/attendance`
* GET `/attendance/student/{id}`
* GET `/attendance/date/{date}`

### Courses

* GET `/courses`
* POST `/courses`

### Calendar

* GET `/events`
* POST `/events`

### Timetable

* GET `/timetable`
* POST `/timetable`

---

## 6. 🧩 Architecture

### Layered Architecture:

* **Controller Layer**

  * Handles HTTP requests
* **Service Layer**

  * Business logic implementation
* **Repository Layer**

  * Database interaction
* **Security Layer**

  * JWT + Spring Security

---

## 7. 🗄️ Database Design

### Tables:

* Users
* Students
* Courses
* Attendance
* Academic Events
* Timetable

---

## 8. 🔒 Security Implementation

* JWT-based authentication
* Request filtering using JWT filter
* Password encryption
* Secured API endpoints

---

## 9. 📊 Work Completed So Far

### ✅ Completed:

* Full backend structure
* Authentication with JWT
* CRUD operations for:

  * Students
  * Courses
  * Attendance
  * Calendar
  * Timetable
* Database integration
* Modular architecture

### 🚧 Pending / Improvements:

* Role-based access (Admin / Student / Teacher)
* Validation & error handling
* Swagger API documentation
* Frontend integration
* Performance optimization

---

## 10. 🚀 Future Enhancements

* Dashboard analytics
* Notifications system
* Report generation
* Mobile app integration
* Role-based permissions

---

## 11. ▶️ How to Run

1. Install Java & Maven
2. Configure database in `application.properties`
3. Run:

   ```
   mvn spring-boot:run
   ```

---

## 12. 🧠 Conclusion

The Jeenify backend is a **well-structured and scalable system** that already implements core student management functionalities. It serves as a strong foundation for a complete academic management platform.

---
