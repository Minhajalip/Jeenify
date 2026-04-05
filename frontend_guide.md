# Frontend Guide — Student Tracker API

## Base URL
```
http://localhost:8080
```

---

## Authentication

All protected endpoints require a JWT token in the request header:
```
Authorization: Bearer <token>
```

You get the token from the login response. Store it (in memory or localStorage) and attach it to every request that needs it.

---

## Important — Student ID vs User ID

After login you get a `userId`. But many student-specific endpoints require a `studentId` which is different. Always call the student profile endpoint right after login to get the `studentId`:

```
GET /api/students/me/{userId}
```

---

## Endpoints

### AUTH

#### Register a student (public)
```
POST /api/auth/register
```
No token required. Always creates a `STUDENT` account — any `role` field sent is ignored.

Request body:
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
}
```

Response:
```
Student registered successfully
```

---

#### Register a teacher or admin (admin only)
```
POST /api/auth/register/staff
```
Token required. Role: `ADMIN`

Request body:
```json
{
    "name": "Dr Smith",
    "email": "smith@college.com",
    "password": "password123",
    "role": "TEACHER"
}
```
Role must be `TEACHER` or `ADMIN`. Sending `STUDENT` returns 400.

Response:
```
TEACHER registered successfully
```

---

#### Login
```
POST /api/auth/login
```
No token required.

Request body:
```json
{
    "email": "john@example.com",
    "password": "password123"
}
```

Response:
```json
{
    "token": "eyJhbGci...",
    "role": "ADMIN",
    "name": "John Doe",
    "userId": 1
}
```
Save the `token`, `role`, `userId` and `name` — you'll need them throughout the app.

---

#### Logout
```
POST /api/auth/logout
```
No token required. Logout is handled on the frontend by deleting the stored token.

Response:
```
Logged out successfully
```

---

### STUDENTS

#### Register a student
```
POST /api/students/register
```
No token required.

Request body:
```json
{
    "name": "Jane Student",
    "email": "jane@example.com",
    "password": "password123",
    "studentNumber": "STU001",
    "majorCourseId": 1
}
```

Response:
```
Student registered successfully. Pending approval.
```

Note: Student cannot be assigned courses until an admin approves them.

---

#### Get student profile
```
GET /api/students/me/{userId}
```
Token required. Any role.

Example: `GET /api/students/me/10`

Response:
```json
{
    "studentId": 5,
    "userId": 10,
    "studentNumber": "STU004",
    "majorCourseId": 1,
    "status": "PENDING"
}
```

Note: Students can only view their own profile. Use `studentId` from this response for all student-specific endpoints.

---

#### Approve a student
```
PUT /api/students/approve/{studentId}
```
Token required. Role: `ADMIN`

Example: `PUT /api/students/approve/4`

Response:
```
Student approved successfully
```

---

#### Assign courses to a student
```
POST /api/students/select-courses
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "studentId": 1,
    "courseIds": [1, 2, 3]
}
```

Note: Student must be approved by admin before courses can be assigned.

Response:
```
Courses selected successfully
```

---

### COURSES

#### Get all courses
```
GET /courses
```
No token required.

Response:
```json
[
    {
        "id": 1,
        "courseCode": "CS101",
        "courseName": "Data Structures",
        "departmentId": 1,
        "mainTeacherId": 1
    }
]
```

---

#### Create a course
```
POST /courses
```
Token required. Role: `ADMIN`

Request body:
```json
{
    "courseCode": "MATH101",
    "courseName": "Mathematics",
    "departmentId": 1,
    "mainTeacherId": 1
}
```

Response: Created course object.

---

#### Delete all courses
```
DELETE /courses
```
Token required. Role: `ADMIN`

---

#### Assign a teacher to a course
```
POST /courses/{courseId}/assign-teacher/{teacherId}
```
Token required. Role: `ADMIN`

Example: `POST /courses/1/assign-teacher/2`

Response:
```
Teacher assigned to course successfully
```

Note: Returns 400 if teacher is already assigned to the course.

⚠️ `teacherId` here is the `id` from the `teachers` table, **not** the `userId` from the login response or users table. To get the correct teacher id, query your teachers data separately.

---

#### Remove a teacher from a course
```
DELETE /courses/{courseId}/remove-teacher/{teacherId}
```
Token required. Role: `ADMIN`

Example: `DELETE /courses/1/remove-teacher/2`

Response:
```
Teacher removed from course successfully
```

Note: Returns 400 if teacher is not assigned to the course.

---

### ATTENDANCE

#### Create an attendance session
```
POST /api/attendance/session
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "courseId": 1,
    "teacherId": 1,
    "date": "2026-04-04",
    "period": 1
}
```

Response:
```json
{
    "id": 1,
    "courseId": 1,
    "date": "2026-04-04",
    "period": 1,
    "teacherId": 1
}
```
Note: 
- TEACHER role: teacherId is automatically set from your token, no need to send it.
- ADMIN role: teacherId is required in the request body. Use teachers.id not users.id.
---

#### Mark attendance
```
POST /api/attendance/mark
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
[
    {
        "sessionId": 1,
        "studentId": 1,
        "status": "PRESENT"
    },
    {
        "sessionId": 1,
        "studentId": 2,
        "status": "ABSENT"
    }
]
```
Status values: `PRESENT`, `ABSENT`

Response:
```
Attendance marked successfully
```

---

### ATTENDANCE CLAIMS

#### Submit a claim
```
POST /api/attendance/claims
```
Token required. Role: `STUDENT`, `TEACHER` or `ADMIN`

Request body:
```json
{
    "studentId": 5,
    "sessionId": 1,
    "reason": "I was sick that day"
}
```

Note: Use `studentId` from the student profile, not `userId` from login. Students can only submit claims for themselves.

Response:
```json
{
    "id": 1,
    "studentId": 5,
    "sessionId": 1,
    "reason": "I was sick that day",
    "filePath": null,
    "status": "Pending"
}
```

---

#### View own claims
```
GET /api/attendance/claims/student/{studentId}
```
Token required. Any role.

Example: `GET /api/attendance/claims/student/5`

Note: Students can only view their own claims.

Response: List of claims for that student.

---

#### Delete a claim (only if pending)
```
DELETE /api/attendance/claims/{claimId}/student/{studentId}
```
Token required. Any role.

Example: `DELETE /api/attendance/claims/8/student/5`

Note: Students can only delete their own claims. Returns 400 if claim has already been approved or rejected.

Response:
```
Claim deleted successfully
```

---

#### Get all claims
```
GET /api/attendance/claims
```
Token required. Role: `TEACHER` or `ADMIN`

Response: List of all claims.

---

#### Get claims for a session
```
GET /api/attendance/claims/session/{sessionId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/attendance/claims/session/1`

Response: List of claims for that session.

---

#### Approve a claim
```
PUT /api/attendance/claims/{claimId}/approve
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `PUT /api/attendance/claims/6/approve`

Response: Updated claim object with status `Approved`.

Note: Returns 400 if claim has already been reviewed.

---

#### Reject a claim
```
PUT /api/attendance/claims/{claimId}/reject
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `PUT /api/attendance/claims/7/reject`

Response: Updated claim object with status `Rejected`.

Note: Returns 400 if claim has already been reviewed.

---

### EXAMS

#### Create an exam
```
POST /api/exams
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "courseId": 1,
    "title": "Midterm Exam",
    "description": "Covers chapters 1-5",
    "maxMarks": 100,
    "examDate": "2026-05-01"
}
```

Response: Created exam object.

---

#### Get all exams for a course
```
GET /api/exams/course/{courseId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/exams/course/1`

Response: List of exams for the course.

---

#### Get exams for a student (enrolled courses only)
```
GET /api/exams/student/{studentId}
```
Token required. Any role.

Example: `GET /api/exams/student/1`

Note: Students can only view their own exams.

Response: List of exams for courses the student is enrolled in.

---

#### Update an exam
```
PUT /api/exams/{examId}
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "title": "Midterm Exam Updated",
    "description": "Covers chapters 1-6",
    "maxMarks": 80,
    "examDate": "2026-05-05"
}
```

Response: Updated exam object.

---

#### Delete an exam
```
DELETE /api/exams/{examId}
```
Token required. Role: `TEACHER` or `ADMIN`

Response:
```
Exam deleted successfully
```

---

### ASSIGNMENTS

#### Create an assignment
```
POST /api/assignments
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "courseId": 1,
    "title": "Assignment 1",
    "description": "Implement a linked list",
    "maxMarks": 20,
    "dueDate": "2026-05-10"
}
```

Response: Created assignment object.

---

#### Get all assignments for a course
```
GET /api/assignments/course/{courseId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/assignments/course/1`

Response: List of assignments for the course.

---

#### Get assignments for a student (enrolled courses only)
```
GET /api/assignments/student/{studentId}
```
Token required. Any role.

Example: `GET /api/assignments/student/1`

Note: Students can only view their own assignments.

Response: List of assignments for courses the student is enrolled in.

---

#### Update an assignment
```
PUT /api/assignments/{assignmentId}
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "title": "Assignment 1 Updated",
    "description": "Implement a linked list and binary tree",
    "maxMarks": 25,
    "dueDate": "2026-05-15"
}
```

Response: Updated assignment object.

---

#### Delete an assignment
```
DELETE /api/assignments/{assignmentId}
```
Token required. Role: `TEACHER` or `ADMIN`

Response:
```
Assignment deleted successfully
```

---

### MARKS

#### Enter exam marks for a student
```
POST /api/marks/exam
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "studentId": 1,
    "examId": 1,
    "marksObtained": 85
}
```

Response: Created mark object.

Note: Returns 400 if marks already entered for this student and exam.

---

#### Update exam marks for a student
```
PUT /api/marks/exam/{studentId}/{examId}?marksObtained=90
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `PUT /api/marks/exam/1/1?marksObtained=90`

Response: Updated mark object.

---

#### Get all marks for an exam
```
GET /api/marks/exam/{examId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/marks/exam/1`

Response: List of marks for all students for that exam.

---

#### Get own exam marks (student)
```
GET /api/marks/exam/student/{studentId}
```
Token required. Any role.

Example: `GET /api/marks/exam/student/1`

Note: Students can only view their own marks.

Response: List of all exam marks for the student.

---

#### Enter assignment marks for a student
```
POST /api/marks/assignment
```
Token required. Role: `TEACHER` or `ADMIN`

Request body:
```json
{
    "studentId": 1,
    "assignmentId": 1,
    "marksObtained": 18
}
```

Response: Created mark object.

Note: Returns 400 if marks already entered for this student and assignment.

---

#### Update assignment marks for a student
```
PUT /api/marks/assignment/{studentId}/{assignmentId}?marksObtained=20
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `PUT /api/marks/assignment/1/1?marksObtained=20`

Response: Updated mark object.

---

#### Get all marks for an assignment
```
GET /api/marks/assignment/{assignmentId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/marks/assignment/1`

Response: List of marks for all students for that assignment.

---

#### Get own assignment marks (student)
```
GET /api/marks/assignment/student/{studentId}
```
Token required. Any role.

Example: `GET /api/marks/assignment/student/1`

Note: Students can only view their own marks.

Response: List of all assignment marks for the student.

---

### REPORTS

#### Get attendance report for a student in a course
```
GET /api/reports/attendance/student/{studentId}/course/{courseId}
```
Token required. Any role.

Example: `GET /api/reports/attendance/student/1/course/1`

Note: Students can only view their own report.

Response:
```json
{
    "total_sessions": 10,
    "present_count": 8,
    "attendance_percentage": 80.00
}
```

---

#### Get attendance report for all students in a course
```
GET /api/reports/attendance/course/{courseId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/reports/attendance/course/1`

Response:
```json
[
    {
        "student_id": 1,
        "total_sessions": 10,
        "present_count": 8,
        "attendance_percentage": 80.00
    }
]
```

---

#### Get exam report for a student
```
GET /api/reports/exams/student/{studentId}
```
Token required. Any role.

Example: `GET /api/reports/exams/student/1`

Note: Students can only view their own report.

Response:
```json
[
    {
        "exam_title": "Midterm Exam",
        "max_marks": 100,
        "marks_obtained": 85,
        "percentage": 85.00
    }
]
```

---

#### Get exam report for all students in a course
```
GET /api/reports/exams/course/{courseId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/reports/exams/course/1`

Response: List of exam marks with percentage for all students in the course.

---

#### Get assignment report for a student
```
GET /api/reports/assignments/student/{studentId}
```
Token required. Any role.

Example: `GET /api/reports/assignments/student/1`

Note: Students can only view their own report.

Response:
```json
[
    {
        "assignment_title": "Assignment 1",
        "max_marks": 20,
        "marks_obtained": 18,
        "percentage": 90.00
    }
]
```

---

#### Get assignment report for all students in a course
```
GET /api/reports/assignments/course/{courseId}
```
Token required. Role: `TEACHER` or `ADMIN`

Example: `GET /api/reports/assignments/course/1`

Response: List of assignment marks with percentage for all students in the course.

---

## Roles & Access

| Endpoint | ADMIN | TEACHER | STUDENT |
|---|---|---|---|
| Register/Login | ✅ | ✅ | ✅ |
| Get student profile | ✅ | ✅ | ✅ (own only) |
| Approve student | ✅ | ❌ | ❌ |
| Assign courses to student | ✅ | ✅ | ❌ |
| Create/Delete course | ✅ | ❌ | ❌ |
| Assign/Remove teacher from course | ✅ | ❌ | ❌ |
| Create attendance session | ✅ | ✅ | ❌ |
| Mark attendance | ✅ | ✅ | ❌ |
| Submit/View/Delete own claims | ✅ | ✅ | ✅ (own only) |
| Approve/Reject claims | ✅ | ✅ | ❌ |
| Create/Update/Delete exam | ✅ | ✅ | ❌ |
| View exams | ✅ | ✅ | ✅ (own courses only) |
| Create/Update/Delete assignment | ✅ | ✅ | ❌ |
| View assignments | ✅ | ✅ | ✅ (own courses only) |
| Enter/Update marks | ✅ | ✅ | ❌ |
| View marks | ✅ | ✅ | ✅ (own only) |
| View reports | ✅ | ✅ | ✅ (own only) |

---

## How to use the token in your requests

After login, attach the token to every protected request:

```javascript
// Example using fetch
const response = await fetch('http://localhost:8080/api/exams', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`  // token from login response
    },
    body: JSON.stringify({
        courseId: 1,
        title: 'Midterm Exam',
        description: 'Covers chapters 1-5',
        maxMarks: 100,
        examDate: '2026-05-01'
    })
});
```

---

## Typical Frontend Flow

### Student flow:
1. Register → `POST /api/students/register`
2. Wait for admin approval
3. Login → `POST /api/auth/login` → save token, userId, role
4. Get student profile → `GET /api/students/me/{userId}` → save studentId
5. View courses → `GET /courses`
6. View exams → `GET /api/exams/student/{studentId}`
7. View assignments → `GET /api/assignments/student/{studentId}`
8. View marks → `GET /api/marks/exam/student/{studentId}`
9. Submit attendance claim → `POST /api/attendance/claims`
10. View reports → `GET /api/reports/attendance/student/{studentId}/course/{courseId}`

### Teacher flow:
1. Login → `POST /api/auth/login` → save token, userId, role
2. Create attendance session → `POST /api/attendance/session`
3. Mark attendance → `POST /api/attendance/mark`
4. Create exam → `POST /api/exams`
5. Enter marks → `POST /api/marks/exam`
6. Review claims → `GET /api/attendance/claims`
7. Approve/reject claims → `PUT /api/attendance/claims/{id}/approve`

### Admin flow:
1. Login → `POST /api/auth/login`
2. Register teachers → `POST /api/auth/register/staff` with `role: "TEACHER"`
3. Register other admins → `POST /api/auth/register/staff` with `role: "ADMIN"`
4. Approve students → `PUT /api/students/approve/{studentId}`
5. Create courses → `POST /courses`
6. Assign teachers → `POST /courses/{courseId}/assign-teacher/{teacherId}`
7. Assign courses to students → `POST /api/students/select-courses`
8. View all reports → `GET /api/reports/attendance/course/{courseId}`

---

## Getting Started (Backend Setup)

1. Clone the repo
2. Copy the config template:
   ```
   copy backend\src\main\resources\application-local.properties.example backend\src\main\resources\application-local.properties
   ```
3. Open `application-local.properties` and fill in your DB username and password
4. Run the database SQL files in `/database` folder to set up your schema
5. Start the backend:
   ```
   cd backend
   mvn spring-boot:run
   ```
6. API is live at `http://localhost:8080`
