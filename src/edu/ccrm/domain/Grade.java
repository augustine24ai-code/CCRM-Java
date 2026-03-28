package edu.ccrm.domain;

// This enum demonstrates constructors and fields, as required.
public enum Grade {
    S(10), A(9), B(8), C(7), D(6), E(5), F(0), NOT_GRADED(-1);

    private final int gradePoints;

    Grade(int gradePoints) {
        this.gradePoints = gradePoints;
    }

    public int getGradePoints() {
        return gradePoints;
    }
}