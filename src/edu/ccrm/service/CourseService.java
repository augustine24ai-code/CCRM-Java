package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Department; // Import Department
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseService {
    private final DataStore dataStore;

    public CourseService() { this.dataStore = DataStore.getInstance(); }

    public Course createCourse(String code, String title, int credits, String departmentName, Semester semester) {
        Department department = new Department(departmentName); // Create Department object
        Course newCourse = new Course.Builder(code, title, credits)
                .department(department)
                .semester(semester)
                .build();
        dataStore.addCourse(newCourse);
        return newCourse;
    }

    public List<Course> searchCoursesByDepartment(String departmentName) {
        return dataStore.getCourses().stream()
                .filter(course -> course.getDepartment().getName().equalsIgnoreCase(departmentName)) // Update filter logic
                .collect(Collectors.toList());
    }

    // ... other methods are unchanged ...
    public Optional<Course> findCourseByCode(String code) { /* ... */ }
    public void assignInstructorToCourse(Instructor instructor, Course course) { /* ... */ }
    public List<Course> getAllCourses() { /* ... */ }
    public List<Course> searchCoursesBySemester(Semester semester) { /* ... */ }
}