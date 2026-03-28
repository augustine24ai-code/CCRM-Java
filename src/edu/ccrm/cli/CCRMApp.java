package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.io.FileService;
import edu.ccrm.service.*;
import edu.ccrm.util.DuplicateEnrollmentException;
import edu.ccrm.util.MaxCreditLimitExceededException;
import edu.ccrm.util.ReportGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

public class CCRMApp {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final FileService fileService = new FileService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FileService.ImportedData data = fileService.importData();
        DataStore.getInstance().loadData(data.students(), data.courses());
        
        System.out.println("Welcome to the Campus Course & Records Manager (CCRM)!");

        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1 -> handleAddStudent();
                case 2 -> handleListAllStudents();
                case 3 -> handleAddCourse();
                case 4 -> handleListAllCourses();
                case 5 -> handleEnrollStudent();
                case 6 -> handleAssignGrade();
                case 7 -> handleViewTranscript();
                case 8 -> handleExportData();
                case 9 -> handleBackupData();
                case 10 -> handleSearchCourses();
                case 11 -> handleGenerateReport();
                case 12 -> handleAnonymousClassDemo();
                case 13 -> {
                    System.out.println("Thank you for using CCRM. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void displayMenu() {
        System.out.println("--- CCRM Main Menu ---");
        System.out.println("1. Add New Student");
        System.out.println("2. List All Students");
        System.out.println("3. Add New Course");
        System.out.println("4. List All Courses");
        System.out.println("5. Enroll Student in Course");
        System.out.println("6. Assign Grade to Student");
        System.out.println("7. View Student Transcript");
        System.out.println("8. Export Data to Files");
        System.out.println("9. Backup Data");
        System.out.println("10. Search Courses");
        System.out.println("11. Generate Report");
        System.out.println("12. Anonymous Class Demo");
        System.out.println("13. Exit Application");
        System.out.println("----------------------");
    }

    private static void handleAnonymousClassDemo() {
        System.out.println("--- Anonymous Inner Class Demo ---");
        System.out.print("Enter a partial name to find the first matching student: ");
        String partialName = scanner.nextLine().trim();

        // DEMONSTRATION OF AN ANONYMOUS INNER CLASS
        Predicate<Student> nameContainsPredicate = new Predicate<Student>() {
            @Override
            public boolean test(Student student) {
                return student.getFullName().toLowerCase().contains(partialName.toLowerCase());
            }
        };

        Optional<Student> foundStudent = DataStore.getInstance().getStudents().stream()
                .filter(nameContainsPredicate)
                .findFirst();

        if (foundStudent.isPresent()) {
            System.out.println("Found first matching student:");
            foundStudent.get().printProfile();
        } else {
            System.out.println("No student found with a name containing '" + partialName + "'.");
        }
    }

    private static void handleGenerateReport() {
        ReportGenerator simpleReport = (dataStore) -> {
            long studentCount = dataStore.getStudents().size();
            long courseCount = dataStore.getCourses().size();
            return "--- System Status Report ---\n" +
                   "Total Students: " + studentCount + "\n" +
                   "Total Courses: " + courseCount + "\n" +
                   "----------------------------";
        };
        String report = simpleReport.generateReport(DataStore.getInstance());
        System.out.println(report);
    }
    
    private static void handleSearchCourses() {
        while (true) {
            System.out.println("--- Search Courses Menu ---");
            System.out.println("1. Search by Department");
            System.out.println("2. Search by Semester");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Department name: ");
                    String departmentName = scanner.nextLine().trim();
                    List<Course> results = courseService.searchCoursesByDepartment(departmentName);
                    printCourseSearchResults(results);
                }
                case 2 -> {
                    System.out.print("Enter Semester (SPRING, SUMMER, FALL, WINTER): ");
                    String semesterStr = scanner.nextLine().trim().toUpperCase();
                    try {
                        Semester semester = Semester.valueOf(semesterStr);
                        List<Course> results = courseService.searchCoursesBySemester(semester);
                        printCourseSearchResults(results);
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: Invalid semester.");
                    }
                }
                case 3 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printCourseSearchResults(List<Course> courses) {
        System.out.println("--- Search Results ---");
        if (courses.isEmpty()) {
            System.out.println("No courses found matching your criteria.");
        } else {
            courses.forEach(System.out::println);
        }
        System.out.println("----------------------");
    }

    private static void handleBackupData() {
        System.out.println("--- Creating Backup ---");
        fileService.backupData();
    }
    
    private static void handleExportData() {
        System.out.println("--- Exporting Data ---");
        List<Student> allStudents = DataStore.getInstance().getStudents();
        List<Course> allCourses = DataStore.getInstance().getCourses();
        fileService.exportData(allStudents, allCourses);
    }
    
    private static void handleAddStudent() {
        System.out.println("--- Add New Student ---");
        try {
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Registration Number (e.g., 21BCE0001): ");
            String regNo = scanner.nextLine().trim();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine().trim());
            Student newStudent = studentService.createStudent(fullName, email, dob, regNo);
            System.out.println("\nSUCCESS: Student '" + newStudent.getFullName() + "' was added successfully!");
        } catch (DateTimeParseException e) {
            System.out.println("\nERROR: Invalid date format. Please use YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("\nERROR: Could not add student. Please check your input.");
        }
    }

    private static void handleListAllStudents() {
        System.out.println("--- List of All Students ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
        } else {
            for (Student student : students) {
                student.printProfile();
            }
        }
        System.out.println("----------------------------");
    }

    private static void handleAddCourse() {
        System.out.println("--- Add New Course ---");
        try {
            System.out.print("Enter Course Code (e.g., CS101): ");
            String code = scanner.nextLine().trim();
            System.out.print("Enter Course Title: ");
            String title = scanner.nextLine().trim();
            System.out.print("Enter Credits: ");
            int credits = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Department Name: ");
            String deptName = scanner.nextLine().trim();
            Course newCourse = courseService.createCourse(code, title, credits, deptName, Semester.FALL);
            System.out.println("\nSUCCESS: Course '" + newCourse.getTitle() + "' was added successfully!");
        } catch (Exception e) {
            System.out.println("\nERROR: Invalid input. Please check the values you entered.");
        }
    }

    private static void handleListAllCourses() {
        System.out.println("--- List of All Courses ---");
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found in the system.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
        System.out.println("---------------------------");
    }

    private static void handleEnrollStudent() {
        System.out.println("--- Enroll Student in Course ---");
        System.out.print("Enter Student's Registration Number: ");
        String regNo = scanner.nextLine().trim();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isEmpty()) {
            System.out.println("ERROR: Student not found with Registration Number: " + regNo);
            return;
        }
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim();
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        if (courseOpt.isEmpty()) {
            System.out.println("ERROR: Course not found with code: " + courseCode);
            return;
        }
        try {
            enrollmentService.enrollStudent(studentOpt.get(), courseOpt.get());
            System.out.println("\nSUCCESS: Enrollment successful!");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("\nENROLLMENT FAILED: " + e.getMessage());
        }
    }

    private static void handleAssignGrade() {
        System.out.println("--- Assign Grade ---");
        System.out.print("Enter Student's Registration Number: ");
        String regNo = scanner.nextLine().trim();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isEmpty()) {
            System.out.println("ERROR: Student not found.");
            return;
        }
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim();
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        if (courseOpt.isEmpty()) {
            System.out.println("ERROR: Course not found.");
            return;
        }
        System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
        String gradeStr = scanner.nextLine().trim().toUpperCase();
        try {
            Grade grade = Grade.valueOf(gradeStr);
            boolean success = enrollmentService.assignGrade(studentOpt.get(), courseOpt.get(), grade);
            if (success) {
                System.out.println("\nSUCCESS: Grade assigned successfully.");
            } else {
                System.out.println("\nFAILURE: Could not assign grade.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nERROR: Invalid grade entered. Please use one of S, A, B, C, D, E, F.");
        }
    }

    private static void handleViewTranscript() {
        System.out.print("Enter Student's Registration Number: ");
        String regNo = scanner.nextLine().trim();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isPresent()) {
            studentOpt.get().printTranscript();
        } else {
            System.out.println("ERROR: Student not found.");
        }
    }
}