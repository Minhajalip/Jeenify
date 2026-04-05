package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.CourseEnrollmentRepository;
import com.tracker.studentracker.repository.StudentRepository;
import com.tracker.studentracker.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.tracker.studentracker.repository.CourseTeacherRepository;

import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.TeacherRepository;

@Service
public class StudentServices {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepo;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Transactional
    public void createStudent(Long userId, String studentNumber, int majorCourseId){

        Student student = new Student();
        student.setStudentId(userId.intValue());
        student.setUserId(userId);
        student.setStudentNumber(studentNumber);
        student.setMajorCourseId(majorCourseId);
        student.setStatus(Student.Status.PENDING);

        studentRepo.save(student);
    }

    @Transactional
    public void selectCourses(int studentId, List<Integer> courseIds, String role, Long currentUserId) {

        // Step 1: fetch student
        Student student = studentRepo.findById((long) studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        // Step 2: check approval
        if (student.getStatus() != Student.Status.APPROVED) {
            throw new RuntimeException("Student not approved");
        }

        // Step 3: validate courses
        if (courseIds == null || courseIds.isEmpty()) {
            throw new RuntimeException("No courses selected");
        }

        // Step 4: ownership check for teachers
        if (role.equals("TEACHER")) {
            Teacher teacher = teacherRepository.findByUserId(currentUserId);
            if (teacher == null) {
                throw new RuntimeException("Teacher profile not found");
            }
            for (Integer courseId : courseIds) {
                if (!courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacher.getId().intValue())) {
                    throw new RuntimeException("Access denied: you are not assigned to course " + courseId);
                }
            }
        }
        // ADMIN skips ownership check entirely

        // Step 5: enroll
        for (Integer courseId : courseIds) {
            if (!enrollmentRepo.isStudentEnrolled(studentId, courseId)) {
                enrollmentRepo.save(studentId, courseId);
            }
        }
    }
    @Transactional
    public void approveStudent(int studentId){
        int rows = studentRepo.approveStudent(studentId);
        if(rows == 0){
            throw new RuntimeException("Student not found");
        }
    }
    public Student getStudentByUserId(Long userId){
        return studentRepo.findByUserId(userId);
    }
}