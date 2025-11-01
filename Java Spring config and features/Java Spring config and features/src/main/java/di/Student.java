package com.example.di;

// Class that depends on Course
public class Student {
    private final String studentName;
    private final Course course; // Dependency

    // Constructor Injection
    public Student(String studentName, Course course) {
        this.studentName = studentName;
        this.course = course;
    }

    public void displayEnrollmentDetails() {
        System.out.println("\n--- Dependency Injection Demonstration ---");
        System.out.println("Student: " + studentName);
        System.out.println(course.getCourseInfo());
        System.out.println("------------------------------------------");
    }
}
