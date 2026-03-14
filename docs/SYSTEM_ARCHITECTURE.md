# System Architecture

Jeenify – Student Attendance & Assessment Management System

---

# 1. System Overview

Jeenify is a web-based academic management platform designed to manage student registration, attendance tracking, academic scheduling, and performance monitoring.

The system allows administrators, teachers, and students to manage academic activities through a centralized platform.

Jeenify follows a three-tier architecture consisting of:

1. Presentation Layer (Frontend)
2. Application Layer (Backend)
3. Data Layer (Database)

This architecture ensures modularity, scalability, and maintainability.

---

# 2. Architecture Layers

## 2.1 Presentation Layer (Frontend)

The Presentation Layer is responsible for user interaction and interface rendering.

Technologies Used

- HTML
- CSS
- JavaScript

### Responsibilities

- Display user interfaces
- Handle form inputs
- Send requests to backend APIs
- Display returned data to users

Main Interfaces

Admin Interfaces

- Teacher management
- Course management
- Academic calendar management
- System analytics dashboard

Teacher Interfaces

- Attendance marking
- Exam creation
- Assignment creation
- Timetable viewing
- Attendance claim management

Student Interfaces

- Student dashboard
- Attendance tracking
- Marks viewing
- Timetable viewing
- Academic calendar viewing
- Attendance claim submission

---

## 2.2 Application Layer (Backend)

The Application Layer processes business logic and system operations.

Technologies Used

- Java (Servlets / Spring Boot)
- Apache Tomcat (Application Server)

### Responsibilities

- Handle authentication
- Implement system business logic
- Process attendance data
- Manage course scheduling
- Process exam and assignment data
- Handle database communication
- Provide APIs for frontend communication

Core Backend Modules

Authentication Module

- Login validation
- Role-based access control
- Session management
- Password hashing

Student Management Module

- Student registration
- Student approval workflow
- Course enrollment

Attendance Module

- Attendance session creation
- Attendance recording
- Attendance percentage calculation
- Attendance claim processing

Assessment Module

- Exam creation
- Assignment creation
- Marks entry
- Performance tracking

Timetable Module

- Weekly timetable creation
- Teacher assignment to periods
- Period configuration

Academic Calendar Module

- Academic event creation
- Event management
- Event retrieval

Dashboard Module

- Student performance analytics
- Attendance statistics
- Course activity summaries

---

## 2.3 Data Layer (Database)

The Data Layer stores and manages persistent system data.

Technology Used

- MySQL Database

### Responsibilities

- Store student records
- Store teacher information
- Store course data
- Store attendance records
- Store exam and assignment data
- Store timetable schedules
- Store academic calendar events

Core Database Tables

User Tables

- users
- students
- teachers

Course Management

- courses
- course_enrollments
- course_teachers

Scheduling

- timetable
- academic_calendar

Attendance

- attendance_sessions
- attendance_records
- attendance_claims

Assessments

- exams
- assignments
- marks

Tables are connected using primary keys and foreign key relationships to maintain data integrity.

---

# 3. System Workflow

The system operates using the following workflow:

User Interaction
↓
Frontend Interface
↓
HTTP Request to Backend
↓
Business Logic Processing
↓
Database Query Execution
↓
Data Returned to Backend
↓
Response Sent to Frontend
↓
User Interface Updated

---

# 4. Authentication Flow

The system uses role-based authentication.

Login Process

1. User enters email and password
2. Backend verifies credentials against the database
3. Password hash is validated
4. Session is created
5. User is redirected to the correct dashboard based on role

Only approved users are allowed to log in.

---

# 5. Deployment Architecture

The system will be deployed using the following infrastructure:

## Client Layer

- Web Browser (Chrome, Firefox, Edge)

## Application Layer

- Apache Tomcat Server
- Java Backend Application

## Database Layer

- MySQL Database Server

## Deployment Flow

Client Browser
↓
HTTP Request
↓
Apache Tomcat Server
↓
Java Backend Application
↓
MySQL Database
↓
Response Returned to Browser

---

# 6. Scalability Considerations

The architecture supports future improvements such as:

- Mobile application integration
- API-based microservices
- Cloud database deployment
- Notification systems
- Analytics modules

---

# 7. Security Considerations

Security measures implemented include:

- Password hashing
- Role-based access control
- Session management
- Input validation
- Database integrity constraints

---

# 8. Benefits of the Architecture

This architecture provides:

- Clear separation of concerns
- Easier maintenance and debugging
- Modular development for team collaboration
- Scalability for future features
- Secure and structured system design
