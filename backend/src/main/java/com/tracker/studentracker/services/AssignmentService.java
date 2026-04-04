package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Assignment;
import com.tracker.studentracker.repository.AssignmentRepository;
import com.tracker.studentracker.repository.CourseEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    // TEACHER/ADMIN - create assignment
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // TEACHER/ADMIN - get all assignments for a course
    public List<Assignment> getAssignmentsForCourse(int courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    // TEACHER/ADMIN - update assignment
    public Assignment updateAssignment(int assignmentId, Assignment updated) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignment.setTitle(updated.getTitle());
        assignment.setDescription(updated.getDescription());
        assignment.setMaxMarks(updated.getMaxMarks());
        assignment.setDueDate(updated.getDueDate());
        return assignmentRepository.save(assignment);
    }

    // TEACHER/ADMIN - delete assignment
    public void deleteAssignment(int assignmentId) {
        if (!assignmentRepository.existsById(assignmentId)) {
            throw new RuntimeException("Assignment not found");
        }
        assignmentRepository.deleteById(assignmentId);
    }

    // STUDENT - get assignments for enrolled courses
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