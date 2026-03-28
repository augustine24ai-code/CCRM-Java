package edu.ccrm.domain;

import java.time.LocalDate;

public class Instructor extends Person {

    private String department;

    public Instructor(String fullName, String email, LocalDate dateOfBirth, String department) {
        super(fullName, email, dateOfBirth);
        this.department = department;
    }

    @Override
    public void printProfile() {
        System.out.println("--- Instructor Profile ---");
        System.out.println("ID: " + getId());
        System.out.println("Full Name: " + getFullName());
        System.out.println("Department: " + this.department);
        System.out.println("Email: " + getEmail());
        System.out.println("--------------------------");
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}