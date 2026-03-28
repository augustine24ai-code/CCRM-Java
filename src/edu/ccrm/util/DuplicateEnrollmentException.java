package edu.ccrm.util;

public class DuplicateEnrollmentException extends Exception {
    
    private static final long serialVersionUID = 1L; // Add this line

    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}