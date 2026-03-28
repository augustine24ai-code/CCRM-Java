package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Student.StudentStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class StudentService {

    private final DataStore dataStore;

    public StudentService() {
        // Get the single instance of the DataStore
        this.dataStore = DataStore.getInstance();
    }

    /**
     * Creates a new student and adds them to the data store.
     */
    public Student createStudent(String fullName, String email, LocalDate dateOfBirth, String regNo) {
        Student newStudent = new Student(fullName, email, dateOfBirth, regNo);
        dataStore.addStudent(newStudent);
        return newStudent;
    }

    /**
     * Finds a student by their registration number.
     * Using Optional is a good practice for methods that might not find a result.
     */
    public Optional<Student> findStudentByRegNo(String regNo) {
        return dataStore.getStudents().stream()
                .filter(student -> student.getRegNo().equalsIgnoreCase(regNo))
                .findFirst();
    }
    
    /**
     * Returns a list of all students in the system.
     */
    public List<Student> getAllStudents() {
        return dataStore.getStudents();
    }

    /**
     * Updates the status of a given student.
     */
    public boolean updateStudentStatus(String regNo, StudentStatus newStatus) {
        Optional<Student> studentOptional = findStudentByRegNo(regNo);
        if (studentOptional.isPresent()) {
            studentOptional.get().setStatus(newStatus);
            return true; // Indicates success
        }
        return false; // Indicates student not found
    }
}