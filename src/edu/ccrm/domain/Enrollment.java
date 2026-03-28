package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private final Course course;
    private final Student student;
    private final LocalDate enrollmentDate;
    private Grade grade;

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.enrollmentDate = LocalDate.now(); // Use current date for enrollment
        this.grade = Grade.NOT_GRADED; // Default grade
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
}