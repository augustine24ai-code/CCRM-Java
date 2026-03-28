package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final DataStore instance = new DataStore();
    private List<Student> students;
    private List<Course> courses;

    private DataStore() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public static DataStore getInstance() {
        return instance;
    }

    // NEW METHOD TO LOAD DATA
    public void loadData(List<Student> students, List<Course> courses) {
        this.students = new ArrayList<>(students);
        this.courses = new ArrayList<>(courses);
    }
    
    public List<Student> getStudents() { return students; }
    public List<Course> getCourses() { return courses; }
    public void addStudent(Student student) { this.students.add(student); }
    public void addCourse(Course course) { this.courses.add(course); }
}