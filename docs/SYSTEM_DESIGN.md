
# SYSTEM_DESIGN.md
## Jeenify – Student Attendance & Assessment Management System

## Project Overview
Jeenify is a web-based academic management platform designed to manage student registration, course enrollment, attendance tracking, exams, assignments, and academic performance.

The system supports three main user roles:

- Admin
- Teacher
- Student

Each role has different permissions and responsibilities.

---

# 1. User Roles

## Admin
The admin has full system control and manages platform-level configuration.

Responsibilities:
- Create and manage teacher accounts
- Manage courses and assign teachers
- View all students, teachers, attendance records, and marks
- Reset passwords and manage permissions
- View analytics and reports
- Manage system settings
- Manage academic calendar

---

## Teachers
Teachers conduct academic activities within their assigned courses.

Two types of teachers exist:

### Main Course Teacher
Course-level administrator responsible for managing course operations.

Permissions:
- Approve or reject student registration requests
- Manage students enrolled in the course
- Assign students to courses
- Manage course timetable
- Configure number of periods per day (default: 6)
- Assign teachers to class periods
- View course analytics
- Approve or reject attendance claims

### Normal Teacher
Handles daily teaching activities.

Permissions:
- Mark attendance
- View enrolled students
- Create exams
- Create assignments
- Enter marks or grades
- View course timetable

---

## Students
Students access the system after approval from the Main Course Teacher.

Features:
- Personal dashboard
- View attendance percentage
- View course-wise attendance
- View exam marks and assignment marks
- View timetable
- View teacher information
- Submit attendance claims

---

# 2. Student Registration Workflow

1. Student signs up with:
   - Full name
   - Email
   - Password
   - Student ID
   - Major course

2. Request stored as pending approval.

3. Main Course Teacher reviews the request.

4. Teacher approves or rejects the registration.

5. Approved students become active users.

---

# 3. Attendance System

Attendance is tracked **per period**.

Default configuration:
- 6 periods per day
- 1 hour per period

Teachers mark attendance by selecting:

- Course
- Date
- Period

Students are marked **Present or Absent**.

Attendance percentage is calculated automatically.

---

# 4. Attendance Claim System

Students can submit attendance claims if they were incorrectly marked absent.

Claim includes:
- Class date
- Period number
- Reason
- Optional supporting file

Teacher can approve or reject the claim.

---

# 5. Assessment System

Teachers can create:

- Exams
- Assignments

For each assessment:
- Title
- Description
- Maximum marks

Teachers enter marks for each student.

Students can view marks through their dashboard.

---

# 6. Timetable System

The timetable organizes weekly course schedules.

Default setup:
- 6 periods per day
- 1 hour per period
- Monday–Friday academic week

Main Course Teachers manage:

- Weekly timetable
- Subject assignment
- Teacher assignment
- Period configuration

Students and teachers can view the timetable from their dashboards.

---

# 7. Academic Calendar

The Academic Calendar defines major academic events throughout the semester.

Managed by Admin.

Examples:
- Semester start/end
- Mid-term exams
- Final exams
- Assignment deadlines
- Holidays

Students and teachers can view upcoming academic events.

---

# 8. Dashboards

## Student Dashboard
Shows:
- Overall attendance percentage
- Course-wise attendance
- Exam marks
- Assignment marks
- Timetable
- Academic calendar events

## Teacher Dashboard
Shows:
- Course attendance statistics
- Student performance
- Pending attendance claims
- Timetable overview

---

# 9. Database Tables

Core tables include:

- users
- students
- teachers
- courses
- course_enrollments
- course_teachers
- timetable
- attendance_sessions
- attendance_records
- attendance_claims
- exams
- assignments
- marks
- academic_calendar

All tables are connected using primary and foreign keys.

---

# 10. Authentication System

Role-based authentication is used.

Login process:

1. User enters email and password
2. System verifies credentials
3. Session is created
4. User redirected based on role

Passwords are securely stored using hashing.

Only approved users can access the system.

---

# 11. System Modules

1. Authentication Module
2. Student Management Module
3. Attendance Management Module
4. Assessment Module
5. Timetable Management Module
6. Academic Calendar Module
7. Dashboard Module
8. Reports & Analytics Module

---

# 12. Benefits

The system provides:

- Centralized academic management
- Automated attendance tracking
- Organized timetable management
- Clear academic calendar
- Performance monitoring
