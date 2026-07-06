# Attendance API Documentation

## Base URL

http://localhost:8080/api/attendance

---

## Authentication

All endpoints require JWT authentication.

### Header:

Authorization: Bearer <your_token>

---

## 1. Create Attendance Session

### Endpoint:

POST /create-session

### Description:

Creates a new attendance session for a course.

### Request Body:

```json
{
  "courseId": 1,
  "teacherId": 2,
  "date": "2026-03-29",
  "period": 1
}
```

### Response:

```json
"Attendance session created successfully"
```

---

## 2. Mark Attendance

### Endpoint:

POST /mark

### Description:

Marks attendance for multiple students in a session.

### Request Body:

```json
{
  "sessionId": 1,
  "records": [
    {
      "studentId": 1,
      "status": "PRESENT"
    },
    {
      "studentId": 2,
      "status": "ABSENT"
    }
  ]
}
```

### Response:

```json
"Attendance marked successfully"
```

---

## 3. Get Attendance by Session

### Endpoint:

GET /session/{sessionId}

### Description:

Fetches all attendance records for a given session.

### Example:

GET /session/1

### Response:

```json
[
  {
    "studentId": 1,
    "status": "PRESENT"
  },
  {
    "studentId": 2,
    "status": "ABSENT"
  }
]
```

---

## Notes

* `status` can be:

  * PRESENT
  * ABSENT

* All endpoints require a valid JWT token.

* Ensure database tables:

  * attendance_sessions
  * attendance_records
    are created before using these APIs.
