package edu.ccrm.domain;

public class Course {

    private final String code;
    private final String title;
    private final int credits;
    private final Department department;
    private final Semester semester;
    private Instructor instructor;

    private Course(Builder builder) {
        // The new assertion is here
        assert builder.credits > 0 : "Course credits must be positive.";

        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.department = builder.department;
        this.semester = builder.semester;
        this.instructor = builder.instructor;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public Department getDepartment() {
        return department;
    }

    public Semester getSemester() {
        return semester;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        String instructorName = (this.instructor != null) ? this.instructor.getFullName() : "Not Assigned";
        return String.format("Course[Code=%s, Title='%s', Dept=%s, Credits=%d, Instructor=%s]",
                this.code, this.title, this.department.getName(), this.credits, instructorName);
    }

    // --- The Static Nested Builder Class ---
    public static class Builder {
        private final String code;
        private final String title;
        private final int credits;
        private Department department = new Department("General");
        private Semester semester = Semester.FALL;
        private Instructor instructor = null;

        public Builder(String code, String title, int credits) {
            this.code = code;
            this.title = title;
            this.credits = credits;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}