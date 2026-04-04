package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Student;
import com.tracker.studentracker.repository.CourseEnrollmentRepository;
import com.tracker.studentracker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServices {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepo;

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
    public void selectCourses(int studentId, List<Integer> courseIds){

        // Step 1: fetch student
        Student student = studentRepo.findById((long) studentId); 

        // Step 2: check approval
        if(student.getStatus() != Student.Status.APPROVED){
            throw new RuntimeException("Student not approved by admin");
        }

        // Step 3: validate courses
        if(courseIds == null || courseIds.isEmpty()){
            throw new RuntimeException("No courses selected");
        }

        // Step 4: insert enrollments
        for(Integer courseId : courseIds){
            enrollmentRepo.save(studentId, courseId);
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