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

## Endpoints

### AUTH

#### Register a user (Admin/Teacher use only)
```
POST /api/auth/register
```
No token required.

Request body:
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "ADMIN"
}
```
Roles: `ADMIN`, `TEACHER`, `STUDENT`

Response:
```
User registered successfully
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

Response:
```json
{
    "id": 2,
    "courseCode": "MATH101",
    "courseName": "Mathematics",
    "departmentId": 1,
    "mainTeacherId": 1
}
```

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

Response:
```json
{
    "examId": 1,
    "courseId": 1,
    "title": "Midterm Exam",
    "description": "Covers chapters 1-5",
    "maxMarks": 100,
    "examDate": "2026-05-01"
}
```

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

Response:
```json
{
    "assignmentId": 1,
    "courseId": 1,
    "title": "Assignment 1",
    "description": "Implement a linked list",
    "maxMarks": 20,
    "dueDate": "2026-05-10"
}
```

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

Response: List of all assignment marks for the student.

---

## Roles & Access

| Endpoint | ADMIN | TEACHER | STUDENT |
|---|---|---|---|
| Register/Login | ✅ | ✅ | ✅ |
| Approve student | ✅ | ❌ | ❌ |
| Assign courses to student | ✅ | ✅ | ❌ |
| Create/Delete course | ✅ | ❌ | ❌ |
| Assign/Remove teacher from course | ✅ | ❌ | ❌ |
| Create attendance session | ✅ | ✅ | ❌ |
| Mark attendance | ✅ | ✅ | ❌ |
| Create/Update/Delete exam | ✅ | ✅ | ❌ |
| View exams | ✅ | ✅ | ✅ (own courses only) |
| Create/Update/Delete assignment | ✅ | ✅ | ❌ |
| View assignments | ✅ | ✅ | ✅ (own courses only) |
| Enter/Update marks | ✅ | ✅ | ❌ |
| View marks | ✅ | ✅ | ✅ (own only) |

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
