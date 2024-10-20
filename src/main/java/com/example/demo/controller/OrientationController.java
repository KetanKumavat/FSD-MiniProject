package com.example.demo.controller;

import com.example.demo.entity.Orientation;
import com.example.demo.entity.Student;
import com.example.demo.repository.OrientationRepository;
import com.example.demo.repository.StudentRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orientations")
@Tag(name = "Orientation Session", description = "Orientation Session")
public class OrientationController {

    @Autowired
    private OrientationRepository orientationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Orientation> getAllOrientations() {
        return orientationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orientation> getOrientationById(@PathVariable int id) {
        return orientationRepository.findById(id)
            .map(orientation -> new ResponseEntity<>(orientation, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Orientation> createOrientation(@RequestBody Orientation orientation) {
        Set<Student> persistedStudents = new HashSet<>();
        for (Student student : orientation.getAttendees()) {
            Student persistedStudent = studentRepository.findById(student.getStudentID())
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + student.getStudentID()));
            persistedStudents.add(persistedStudent);
        }
        orientation.setAttendees(persistedStudents);
        Orientation savedOrientation = orientationRepository.save(orientation);
        return new ResponseEntity<>(savedOrientation, HttpStatus.CREATED);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Orientation> patchOrientation(@PathVariable int id, @RequestBody Orientation orientation) {
    return orientationRepository.findById(id)
        .map(existingOrientation -> {
            // Fetch and persist students
            Set<Student> persistedStudents = new HashSet<>();
            if (orientation.getAttendees() != null) {
                for (Student student : orientation.getAttendees()) {
                    Student persistedStudent = studentRepository.findById(student.getStudentID())
                        .orElseThrow(() -> new IllegalArgumentException("Student not found: " + student.getStudentID()));
                    persistedStudents.add(persistedStudent);
                }
            }
            existingOrientation.setAttendees(persistedStudents);

            // Copy non-null properties excluding the ID
            BeanUtils.copyProperties(orientation, existingOrientation, getNullPropertyNames(orientation, "departmentID"));
            Orientation updatedOrientation = orientationRepository.save(existingOrientation);
            return new ResponseEntity<>(updatedOrientation, HttpStatus.OK);
        })
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private String[] getNullPropertyNames(Object source, String... excludeProperties) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {
        Object srcValue = src.getPropertyValue(pd.getName());
        if (srcValue == null) emptyNames.add(pd.getName());
    }
    for (String excludeProperty : excludeProperties) {
        emptyNames.add(excludeProperty);
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
    }

    @PutMapping("/update/{id}") public ResponseEntity<Orientation> updateOrientation(@PathVariable int id, @RequestBody Orientation orientation) { if (!orientationRepository.existsById(id)) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); } orientation.setDepartmentID(id); Orientation updatedOrientation = orientationRepository.save(orientation); return new ResponseEntity<>(updatedOrientation, HttpStatus.OK); }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrientation(@PathVariable int id) {
        if (!orientationRepository.existsById(id)) {
            return new ResponseEntity<>("Orientation not found with id " + id, HttpStatus.NOT_FOUND);
        }
        orientationRepository.deleteById(id);
        return new ResponseEntity<>("Orientation deleted successfully", HttpStatus.OK);
    }
}