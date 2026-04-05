package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Assignment;
import com.tracker.studentracker.models.Teacher;
import com.tracker.studentracker.repository.AssignmentRepository;
import com.tracker.studentracker.repository.CourseEnrollmentRepository;
import com.tracker.studentracker.repository.CourseTeacherRepository;
import com.tracker.studentracker.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private void checkTeacherOwnership(int courseId, Long currentUserId) {
        Teacher teacher = teacherRepository.findByUserId(currentUserId);
        if (teacher == null) throw new RuntimeException("Teacher profile not found");
        if (!courseTeacherRepository.existsByCourseIdAndTeacherId(courseId, teacher.getId().intValue())) {
            throw new RuntimeException("Access denied: you are not assigned to this course");
        }
    }

    public Assignment createAssignment(Assignment assignment, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(assignment.getCourseId(), currentUserId);
        }
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentsForCourse(int courseId, String role, Long currentUserId) {
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(courseId, currentUserId);
        }
        return assignmentRepository.findByCourseId(courseId);
    }

    public Assignment updateAssignment(int assignmentId, Assignment updated, String role, Long currentUserId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(assignment.getCourseId(), currentUserId);
        }
        assignment.setTitle(updated.getTitle());
        assignment.setDescription(updated.getDescription());
        assignment.setMaxMarks(updated.getMaxMarks());
        assignment.setDueDate(updated.getDueDate());
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(int assignmentId, String role, Long currentUserId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        if (role.equals("TEACHER")) {
            checkTeacherOwnership(assignment.getCourseId(), currentUserId);
        }
        assignmentRepository.deleteById(assignmentId);
    }

    public List<Assignment> getAssignmentsForStudent(int studentId) {
        List<Integer> enrolledCourseIds = enrollmentRepository.getEnrolledCourseIds(studentId);
        if (enrolledCourseIds.isEmpty()) {
            throw new RuntimeException("Student is not enrolled in any courses");
        }
        return assignmentRepository.findAll().stream()
                .filter(assignment -> enrolledCourseIds.contains(assignment.getCourseId()))
                .toList();
    }
}