# Campus Course & Records Manager (CCRM)

## 1. Project Overview
The Campus Course & Records Manager (CCRM) is a console-based Java application designed to manage students, courses, enrollments, and grades for an educational institute. It demonstrates a comprehensive understanding of core and advanced Java SE concepts, including Object-Oriented Programming, modern APIs, design patterns, and robust error handling.

**Core Features:**
* **Student Management:** Add, list, and view student profiles and transcripts.
* **Course Management:** Add, list, and search courses by department or semester.
* **Enrollment & Grading:** Enroll students in courses, assign grades, and compute GPA.
* **Data Persistence:** Import/Export data to CSV files and create timestamped backups.

---

## 2. How to Run
1.  **Prerequisites:** JDK 17 or later must be installed.
2.  **Clone Repository:** `git clone https://github.com/augustine24ai-code/CCRM-Java.git`
3.  **Navigate to Project:** Open the project in an IDE like Eclipse or IntelliJ.
4.  **Enable Assertions:** In your IDE's run configuration, add `-ea` to the VM arguments.
5.  **Run:** Execute the `main` method in `edu.ccrm.cli.CCRMApp.java`.

---

## 3. Java Platform Explanations

### Evolution of Java
* **1995:** Java 1.0 is released by Sun Microsystems.
* **2004:** J2SE 5.0 (Tiger) brings major features like Generics, Enums, and Annotations.
* **2014:** Java SE 8 is a landmark release with Lambda Expressions and the Stream API.
* **2018:** Java SE 11 is released as a Long-Term Support (LTS) version.
* **2023:** Java SE 21 is released as the latest LTS version, featuring Virtual Threads.

### Java ME vs. SE vs. EE

| Feature         | Java ME (Micro Edition)                          | Java SE (Standard Edition)                            | Java EE (Enterprise Edition)                               |
| :-------------- | :----------------------------------------------- | :---------------------------------------------------- | :--------------------------------------------------------- |
| **Primary Use** | Embedded systems, IoT, old mobile devices.       | General-purpose desktop & server applications.        | Large-scale, distributed, enterprise-level applications.   |
| **Core API** | A subset of the Java SE API.                     | The core Java programming platform.                   | Builds on top of Java SE with extensive libraries.       |
| **Example App** | A simple game on a feature phone.                | This CCRM project.                                    | An online banking system or e-commerce website.            |

### JDK vs. JRE vs. JVM
* **JVM (Java Virtual Machine):** An abstract machine that runs Java bytecode, making Java platform-independent.
* **JRE (Java Runtime Environment):** Includes the JVM and core libraries needed to *run* a Java application.
* **JDK (Java Development Kit):** Includes the JRE plus tools needed to *develop* Java applications, like the compiler (`javac`).

---

## 4. Mapping Table: Requirements to Code

| Requirement                | Location in Code                                                                          |
| :------------------------- | :---------------------------------------------------------------------------------------- |
| **OOP: Encapsulation** | `domain/Person.java` (private fields with public getters/setters)                         |
| **OOP: Inheritance** | `domain/Student.java` (the line `public class Student extends Person`)                    |
| **OOP: Abstraction** | `domain/Person.java` (declared as an `abstract class` with an `abstract` method)            |
| **OOP: Polymorphism** | `cli/CCRMApp.java` (`handleListAllStudents` calls the overridden `student.printProfile()`)  |
| **Design Pattern: Singleton**| `service/DataStore.java`                                                                  |
| **Design Pattern: Builder** | `domain/Course.java` (the static nested `Builder` class)                                  |
| **Interfaces** | `util/ReportGenerator.java` (declaration of the interface)                                |
| **Lambda Expression** | `cli/CCRMApp.java` (in `handleGenerateReport`, used to implement `ReportGenerator`)       |
| **Enums with Constructors** | `domain/Grade.java` and `domain/Semester.java`                                            |
| **Custom Exceptions** | `util/DuplicateEnrollmentException.java`                                                  |
| **Exception Handling** | `cli/CCRMApp.java` (in `handleEnrollStudent`, the `try-catch` block)                        |
| **File I/O: NIO.2** | `io/FileService.java` (use of `Path`, `Paths`, `Files.write`, `Files.copy`)                 |
| **Streams API** | `service/CourseService.java` (in `searchCoursesByDepartment`, used to filter)             |
| **Date/Time API** | `domain/Person.java` (the `private LocalDate dateOfBirth;` field)                           |
| **Recursion** | `io/FileService.java` (in `calculateDirectorySize` method, which uses `Files.walk`)       |
| **Nested Class (static)** | `domain/Course.java` (the `public static class Builder`)                                  |
| **Inner Class (non-static)**| `domain/Student.java` (the `private class Transcript`)                                    |
| **Anonymous Inner Class** | `cli/CCRMApp.java` (in `handleAnonymousClassDemo`, used to implement `Predicate<Student>`)  |
| **Immutable Class** | `domain/Department.java`                                                                  |
| **Assertions** | `domain/Course.java` (in the `Course(Builder builder)` constructor)                       |
#
