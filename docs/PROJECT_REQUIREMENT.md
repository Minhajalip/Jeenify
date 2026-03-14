# Project Requirements
## Jeenify – Student Attendance & Assessment Management System

## 1. Project Overview

Jeenify is a **web-based academic management platform** designed to simplify and centralize the management of student records, attendance tracking, course scheduling, assessments, and academic performance.

The platform enables **admins, teachers, and students** to manage academic activities efficiently through a single unified system.

The system provides teachers and administrators with a centralized interface to manage student information, monitor attendance, record assessments, and generate reports efficiently.

The goal of this project is to build a structured and user-friendly system that streamlines academic data management while ensuring reliability, scalability, and ease of use.

---

# 2. Project Objectives

- Develop a centralized system to manage student information.
- Provide a reliable attendance tracking mechanism.
- Allow teachers to record and manage assessments.
- Generate reports for attendance and academic performance.
- Provide a simple dashboard for quick insights.
- Ensure efficient database management for academic records.
- Enable role-based access control (Admin, Teachers, Students).
- Support course enrollment and student approval workflows.
- Manage period-based attendance tracking.
- Facilitate exam and assignment management.
- Track student performance and generate analytics.

---

# 3. Technology Stack

### Backend
- Java (Servlets / Spring Boot)

### Frontend
- HTML
- CSS
- JavaScript

### Database
- MySQL

### Server
- Apache Tomcat

### Version Control
- Git
- GitHub

### Development Tools
- VS Code / IntelliJ IDEA
- MySQL Workbench

---

# 4. System Modules

The system is divided into the following modules.

## 4.1 Authentication Module
- Admin login
- Teacher login
- Student login
- Role-based redirection
- Credential validation
- Session management
- Password hashing

## 4.2 Student Management Module
- Student registration and approval workflow
- Add student
- Update student details
- Delete student records
- View student profiles
- Course enrollment
- Student approval by Main Course Teacher

## 4.3 Course Management Module
- Create and manage courses
- Assign teachers to courses
- Course-level administration
- Main Course Teacher assignment
- Teacher period assignment

## 4.4 Attendance Management Module
- Mark daily attendance (per class period)
- Edit attendance records
- View attendance history
- Calculate attendance statistics and percentages
- Attendance claim submission by students
- Attendance claim approval/rejection by teachers
- Period-based attendance tracking

## 4.5 Timetable Management Module
- Create weekly timetable (6 periods per day, 1 hour per period)
- Assign teachers to periods
- Assign subjects to periods
- View daily schedules
- Access attendance directly from timetable
- Monday – Friday academic week scheduling

## 4.6 Academic Calendar Module
- Manage important academic events throughout the semester
- Semester start and end dates
- Mid-term and final exams scheduling
- Assignment deadlines
- Holidays and study breaks
- Admin configuration of academic events
- Student and teacher dashboard visibility

## 4.7 Assessment Management Module
- Create exams
- Create assignments
- Enter student marks
- Update assessments
- Store assessment records
- Generate performance summaries
- Students view marks on dashboard

## 4.8 Dashboard Module
- Student Dashboard: display overall attendance percentage, course-wise attendance, exam marks, assignment marks, timetable, academic calendar, performance summary
- Teacher Dashboard: display course attendance statistics, student performance reports, pending attendance claims, course activity overview, assigned timetable
- Display student statistics
- Show attendance overview
- Provide quick access to system features
- Analytics and reports

## 4.9 Search & Filter Module
- Search students by name or ID
- Filter students by department
- View filtered records

## 4.10 Report Generation Module
- Attendance reports
- Student performance reports
- Export reports (CSV / PDF if implemented)
- Analytics generation

---

# 5. Functional Requirements

The system must be able to:

- Authenticate users through a login system with role-based redirection.
- Allow admins to create and manage teacher accounts.
- Allow admins to manage courses and assign teachers to courses.
- Allow Main Course Teachers to approve student registrations.
- Allow teachers/admins to manage student records.
- Record daily student attendance per class period.
- Calculate and display attendance percentages automatically.
- Allow students to submit attendance claims with reason and optional supporting documents.
- Allow teachers to approve or reject attendance claims.
- Store and update student assessment marks (exams and assignments).
- Retrieve student data from the database.
- Display summarized information through personalized dashboards.
- Generate attendance and assessment reports.
- Create and manage weekly timetables with teacher-period assignments.
- Manage academic calendar events and display them to users.
- Support course enrollment workflow.
- Display student performance tracking and analytics.

---

# 6. Non-Functional Requirements

### Performance
- The system should respond quickly to user requests.
- Dashboard loading should be efficient with aggregated data.

### Usability
- The interface should be simple and easy to navigate.
- Role-specific interfaces for admins, teachers, and students.
- Clear visual organization of information.

### Reliability
- Data stored in the system should remain consistent and accurate.
- Attendance percentages must be calculated correctly.
- Data integrity through primary and foreign keys.

### Security
- Only authorized users should access the system.
- Role-based access control enforcement.
- Secure session management.
- Password hashing for user credentials.

### Maintainability
- The system code should be organized and modular for future updates.
- Clear separation of frontend, backend, and database layers.
- Well-documented code and APIs.

### Scalability
- System should support growing number of students, courses, and academic records.
- Database optimized for queries and reporting.

---

# 7. Database Requirements

The database should include the following tables:

- **users** - Authentication and user information
- **students** - Student profiles
- **teachers** - Teacher information
- **courses** - Course management
- **course_enrollments** - Student course enrollment
- **course_teachers** - Teacher-course assignments
- **timetable** - Weekly class schedules
- **attendance_sessions** - Attendance session records
- **attendance_records** - Individual student attendance marks
- **attendance_claims** - Student attendance dispute claims
- **exams** - Exam information
- **assignments** - Assignment information
- **marks** - Student assessment marks
- **academic_calendar** - Academic events and scheduling

Relationships must be defined using primary and foreign keys to ensure data integrity.

---

# 8. System Roles & Responsibilities

## Admin
- Create and manage teacher accounts
- Manage courses
- Assign teachers to courses
- Monitor attendance records
- View student performance
- Manage academic calendar
- View timetable schedules
- Configure system settings
- Access analytics and reports

## Main Course Teacher
- Approve student registrations
- Manage course students
- Create course timetables
- Assign teachers to periods
- View course analytics
- Approve attendance claims

## Normal Teacher
- Mark attendance
- Create exams
- Create assignments
- Enter marks
- View course timetable
- View academic calendar

## Student
- View personal dashboard
- View attendance percentage (overall and course-wise)
- View exam and assignment marks
- View timetable
- View academic calendar
- Submit attendance claims

---

# 9. Expected Outcomes

After successful implementation, the system should:

- Provide a centralized platform for managing student data.
- Reduce manual effort in attendance tracking.
- Provide quick insights into student performance.
- Improve the efficiency of academic data management.
- Enable seamless communication between admins, teachers, and students.
- Support scalable academic management for multiple courses and students.

---

# 10. Future Enhancements

Possible future improvements include:

- Mobile application
- Email notifications
- SMS alerts
- Role-based user access refinements
- Mobile-friendly interface
- Automated attendance analytics
- Integration with other academic systems
- Notification systems
- Integration with university ERP systems 

---