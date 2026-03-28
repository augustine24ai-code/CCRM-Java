package edu.ccrm.domain;

// Demonstrates an immutable class.
public final class Department { // final class prevents extension
    private final String name; // final field

    public Department(String name) {
        this.name = name;
    }

    public String getName() { // Only a getter, no setter
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}