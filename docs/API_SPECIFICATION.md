# API Endpoints Specification

## Timetable
- **GET /api/timetable**: Retrieve the timetable for a user.
- **POST /api/timetable**: Create or update a user's timetable entry.

## Academic Calendar
- **GET /api/academic-calendar**: Get the academic calendar, including term dates and holidays.

## Courses
- **GET /api/courses**: List all available courses.
- **POST /api/courses**: Create a new course.
- **GET /api/courses/{id}**: Get details of a specific course.
- **PUT /api/courses/{id}**: Update a course's information.
- **DELETE /api/courses/{id}**: Delete a specific course.

## Exam Creation
- **POST /api/exams**: Create a new exam.
- **GET /api/exams**: List all exams.
- **GET /api/exams/{id}**: Get details of a specific exam.

## Assignment Creation
- **POST /api/assignments**: Create a new assignment.
- **GET /api/assignments**: List all assignments.
- **GET /api/assignments/{id}**: Get details of a specific assignment.

## Attendance Claims
- **POST /api/attendance**: Submit an attendance claim.
- **GET /api/attendance**: Retrieve a user's attendance records.

## Dashboard Endpoints
- **GET /api/dashboard**: Get a user’s dashboard with all relevant information, including assignments, exams, and attendance statistics.