package com.example.di;

// Dependent class
public class Course {
    private final String name;

    public Course(String name) {
        this.name = name;
    }

    public String getCourseInfo() {
        return "Enrolled in Course: " + name;
    }
}
