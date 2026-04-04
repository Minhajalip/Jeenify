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
Note: Student cannot select courses until an admin approves them.

---

#### Select courses (after admin approval)
```
POST /api/students/select-courses
```
Token required. Role: `STUDENT`

Request body:
```json
{
    "studentId": 1,
    "courseIds": [1, 2, 3]
}
```

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
No token required.

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
No token required.

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

## Roles & Access

| Role    | What they can do                                      |
|---------|-------------------------------------------------------|
| ADMIN   | Approve students, manage departments, everything else |
| TEACHER | Create attendance sessions, mark attendance, manage exams/assignments/marks |
| STUDENT | Register, select courses (after approval)             |

---

## How to use the token in your requests

After login, attach the token to every protected request:

```javascript
// Example using fetch
const response = await fetch('http://localhost:8080/api/attendance/session', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`  // token from login response
    },
    body: JSON.stringify({
        courseId: 1,
        teacherId: 1,
        date: '2026-04-04',
        period: 1
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