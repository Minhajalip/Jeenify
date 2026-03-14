# 🚀 Jeenify

### Student Attendance & Assessment Management System

![Java](https://img.shields.io/badge/Backend-Java-orange)
![Frontend](https://img.shields.io/badge/Frontend-HTML%20CSS%20JS-blue)
![Database](https://img.shields.io/badge/Database-MySQL-green)
![Server](https://img.shields.io/badge/Server-Apache%20Tomcat-red)
![Status](https://img.shields.io/badge/Project-Active-brightgreen)

Jeenify is a **web-based academic management platform** designed to simplify and centralize the management of student records, attendance tracking, course scheduling, assessments, and academic performance.

The platform enables **admins, teachers, and students** to manage academic activities efficiently through a single unified system.

---

# 📑 Table of Contents

* Project Overview
* System Roles
* Core Features
* Timetable System
* Academic Calendar
* Attendance System
* Assessment System
* Dashboards
* Technology Stack
* Project Structure
* Installation Guide
* Documentation
* Development Workflow
* Team
* Future Enhancements

---

# 📌 Project Overview

Managing attendance, exams, and academic schedules manually can be inefficient.

Jeenify provides a **centralized digital system** for:

* Student registration and approval
* Course enrollment
* Period-based attendance tracking
* Timetable management
* Academic calendar scheduling
* Exam and assignment management
* Student performance tracking

---

# 👥 System Roles

## 1️⃣ Admin

Admins manage the entire system.

### Responsibilities

* Create and manage teacher accounts
* Manage courses
* Assign teachers to courses
* Monitor attendance records
* View student performance
* Manage academic calendar
* View timetable schedules
* Configure system settings
* Access analytics and reports

---

## 2️⃣ Teachers

Teachers manage academic activities within their courses.

### Main Course Teacher

Course-level administrator.

Responsibilities include:

* Approving student registrations
* Managing course students
* Creating course timetables
* Assigning teachers to periods
* Viewing course analytics
* Approving attendance claims

---

### Normal Teacher

Handles classroom-level activities.

Responsibilities include:

* Mark attendance
* Create exams
* Create assignments
* Enter marks
* View course timetable
* View academic calendar

---

## 3️⃣ Students

Students gain access after approval from the Main Course Teacher.

### Student Features

* Personal dashboard
* View attendance percentage
* View course-wise attendance
* View exam marks
* View assignment marks
* View timetable
* View academic calendar
* Submit attendance claims

---

# 📅 Timetable System

The timetable module organizes **weekly class schedules**.

### Default Setup

* 6 periods per day
* 1 hour per period
* Monday – Friday academic week

### Features

* Create weekly timetable
* Assign teachers to periods
* Assign subjects to periods
* View daily schedules
* Access attendance directly from timetable

---

# 📆 Academic Calendar

The academic calendar manages **important academic events throughout the semester**.

### Managed by Admin

Events include:

* Semester start date
* Semester end date
* Mid-term exams
* Final exams
* Assignment deadlines
* Holidays
* Study breaks

Students and teachers can view events from their dashboards.

---

# 📊 Attendance System

Attendance is tracked **per class period**.

### Process

Teachers select:

* Course
* Date
* Period

Students are marked:

* Present
* Absent

Attendance percentages are **calculated automatically**.

---

# 📝 Attendance Claim System

Students can submit attendance claims if marked absent incorrectly.

Claim includes:

* Date
* Period number
* Reason
* Optional supporting document

Teachers can **approve or reject claims**.

---

# 📚 Assessment System

Teachers can create:

* Exams
* Assignments

Each assessment includes:

* Title
* Description
* Maximum marks

Teachers enter marks and students view them on their dashboard.

---

# 📊 Dashboards

## Student Dashboard

Displays:

* Overall attendance percentage
* Course-wise attendance
* Exam marks
* Assignment marks
* Timetable
* Academic calendar
* Performance summary

---

## Teacher Dashboard

Displays:

* Course attendance statistics
* Student performance reports
* Pending attendance claims
* Course activity overview
* Assigned timetable

---

# 🗄 Database Tables

Main tables include:

* users
* students
* teachers
* courses
* course_enrollments
* course_teachers
* timetable
* attendance_sessions
* attendance_records
* attendance_claims
* exams
* assignments
* marks
* academic_calendar

Tables are linked using **primary keys and foreign keys**.

---

# ⚙️ Technology Stack

### Backend

Java (Servlets / Spring Boot)

### Frontend

HTML
CSS
JavaScript

### Database

MySQL

### Server

Apache Tomcat

### Tools

Git
GitHub
IntelliJ IDEA / VS Code
MySQL Workbench

---

# 🏗 System Architecture

flowchart TD

A[Student / Teacher / Admin Browser]

A --> B[Frontend Layer<br>HTML CSS JavaScript]

B --> C[Backend Layer<br>Java Servlets / Spring Boot]

C --> D[Business Logic]

D --> E[(MySQL Database)]

E --> F[Users]
E --> G[Courses]
E --> H[Attendance]
E --> I[Assessments]
E --> J[Timetable]
E --> K[Academic Calendar]

---

# 🗄 Database Design

erDiagram

USERS ||--o{ STUDENTS : contains
USERS ||--o{ TEACHERS : contains

COURSES ||--o{ COURSE_ENROLLMENTS : has
STUDENTS ||--o{ COURSE_ENROLLMENTS : enrolls

COURSES ||--o{ COURSE_TEACHERS : assigned
TEACHERS ||--o{ COURSE_TEACHERS : teaches

COURSES ||--o{ TIMETABLE : schedules

COURSES ||--o{ ATTENDANCE_SESSIONS : contains
ATTENDANCE_SESSIONS ||--o{ ATTENDANCE_RECORDS : stores
STUDENTS ||--o{ ATTENDANCE_RECORDS : attends

ATTENDANCE_RECORDS ||--o{ ATTENDANCE_CLAIMS : disputes

COURSES ||--o{ EXAMS : has
COURSES ||--o{ ASSIGNMENTS : has

STUDENTS ||--o{ MARKS : receives
EXAMS ||--o{ MARKS : generates
ASSIGNMENTS ||--o{ MARKS : generates

ACADEMIC_CALENDAR {
    int event_id
    string title
    date event_date
}

---

# 📂 Project Structure

```
jeenify
│
├── frontend
│   ├── html
│   ├── css
│   └── js
│
├── backend
│   ├── controllers
│   ├── services
│   ├── models
│   └── config
│
├── database
│   ├── schema.sql
│   └── sample_data.sql
│
├── docs
│   ├── PROJECT_REQUIREMENTS.md
│   ├── SYSTEM_ARCHITECTURE.md
│   ├── DATABASE_SCHEMA.md
│   ├── SYSTEM_DESIGN.md
│   ├── API_SPECIFICATION.md
│   ├── TASK_BOARD.md
│   └── ROADMAP.md
│
└── tests
```

---

# 🖥 Installation

### Clone the Repository

```bash
git clone https://github.com/your-username/jeenify.git
```

### Setup Database

Create database:

```sql
CREATE DATABASE jeenify_db;
```

Import schema:

```
database/schema.sql
```

### Run the Application

Deploy the backend using **Apache Tomcat**.

---

# 📚 Documentation

Detailed documentation is available inside the **docs folder**:

* Project Requirements
* System Architecture
* Database Schema
* API Specification
* System Design
* Development Roadmap

---

# 🔧 Development Workflow

1. Create feature branch
2. Implement feature
3. Create Pull Request
4. Code review
5. Merge into develop branch

---

# 👨‍💻 Team

Project Manager
Minhaj Ali P

Technical Lead
Ahmed Sultan P

Git & Integration Lead
Fadil Rahman

Developed by a collaborative student team.

---

# 🔮 Future Enhancements

* Mobile application
* Email notifications
* SMS alerts
* Attendance analytics
* Integration with university ERP systems

---

# 📜 License

This project is developed for **academic purposes**.
