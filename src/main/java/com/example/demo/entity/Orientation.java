package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orientation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "departmentID")
public class Orientation {
    @Id
    private int departmentID;
    private String departmentName;
    private String location;
    private String facultyName;
    private String time;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Orientation_Attendees",
        joinColumns = @JoinColumn(name = "OrientationID"),
        inverseJoinColumns = @JoinColumn(name = "StudentID")
    )
    private Set<Student> attendees = new HashSet<>();

    // Getters and Setters
    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<Student> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<Student> attendees) {
        this.attendees = attendees;
    }
}