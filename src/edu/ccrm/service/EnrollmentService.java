package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.util.DuplicateEnrollmentException;
import edu.ccrm.util.MaxCreditLimitExceededException;

public class EnrollmentService {
    
    private static final int MAX_CREDITS_PER_SEMESTER = 21;

    public void enrollStudent(Student student, Course course) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        // Rule 1: Check for duplicate enrollment
        boolean isAlreadyEnrolled = student.getEnrollments().stream()
                .anyMatch(enrollment -> enrollment.getCourse().getCode().equals(course.getCode()));

        if (isAlreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student is already enrolled in course " + course.getCode());
        }

        // Rule 2: Check for maximum credit limit
        int currentCredits = student.getEnrollments().stream()
                .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                .sum();
        
        if ((currentCredits + course.getCredits()) > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException("Enrollment failed: Exceeds max credit limit of " + MAX_CREDITS_PER_SEMESTER);
        }

        // If all rules pass, create the enrollment
        Enrollment newEnrollment = new Enrollment(course, student);
        student.addEnrollment(newEnrollment);
    }

    // assignGrade method remains the same
}