package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private StudentStatus status;
    private final List<Enrollment> enrollments;

    // ... constructor and other methods are the same ...

    // DEMONSTRATION OF AN INNER CLASS
    private class Transcript {
        // This inner class can directly access fields of the Student instance, like regNo and enrollments.
        public void print() {
            System.out.println("--- Academic Transcript ---");
            System.out.println("Student: " + getFullName() + " | Reg No: " + regNo); // Accessing outer class members
            System.out.println("---------------------------");

            System.out.println("Enrolled Courses:");
            if (enrollments.isEmpty()) {
                System.out.println("  No courses enrolled.");
            } else {
                for (Enrollment enrollment : enrollments) {
                    Course course = enrollment.getCourse();
                    Grade grade = enrollment.getGrade();
                    System.out.printf("  - %s: %s (%d credits) - Grade: %s\n",
                            course.getCode(), course.getTitle(), course.getCredits(), grade);
                }
            }
            System.out.printf("\nCumulative GPA: %.2f\n", calculateGPA());
            System.out.println("---------------------------");
        }
    }

    // The printTranscript method now uses the inner class
    public void printTranscript() {
        Transcript transcript = new Transcript();
        transcript.print();
    }

    // --- Unchanged methods below ---
    public Student(String fullName, String email, LocalDate dateOfBirth, String regNo) { super(fullName, email, dateOfBirth); this.regNo = regNo; this.status = StudentStatus.ACTIVE; this.enrollments = new ArrayList<>(); }
    public enum StudentStatus { ACTIVE, INACTIVE, GRADUATED; }
    public void addEnrollment(Enrollment enrollment) { this.enrollments.add(enrollment); }
    public List<Enrollment> getEnrollments() { return enrollments; }
    @Override public void printProfile() { System.out.println("--- Student Profile ---\nID: " + getId() + "\nRegistration No: " + this.regNo + "\nFull Name: " + getFullName() + "\nEmail: " + getEmail() + "\nStatus: " + this.status + "\n-----------------------"); }
    public double calculateGPA() { List<Enrollment> gradedEnrollments = this.enrollments.stream().filter(e -> e.getGrade() != null && e.getGrade() != Grade.NOT_GRADED).toList(); if (gradedEnrollments.isEmpty()) { return 0.0; } double totalPoints = gradedEnrollments.stream().mapToDouble(e -> e.getGrade().getGradePoints() * e.getCourse().getCredits()).sum(); int totalCredits = gradedEnrollments.stream().mapToInt(e -> e.getCourse().getCredits()).sum(); if (totalCredits == 0) { return 0.0; } return totalPoints / totalCredits; }
    public String getRegNo() { return regNo; }
    public StudentStatus getStatus() { return status; }
    public void setStatus(StudentStatus status) { this.status = status; }
}