package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {

    private int id;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;

    private static int idCounter = 0;

    public Person(String fullName, String email, LocalDate dateOfBirth) {
        this.id = ++idCounter;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // This is the line that is likely missing from your file.
    public abstract void printProfile();

    // Getters and Setters
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}