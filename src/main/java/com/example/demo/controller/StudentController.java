package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Management", description = "Student Management System")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    private static final Logger logger = Logger.getLogger(StudentController.class.getName());

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a student by their ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        return studentRepository.findById(id)
            .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new student", description = "Create a new student record")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an existing student", description = "Update an existing student record")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        return studentRepository.findById(id)
            .map(existingStudent -> {
                existingStudent.setFirstName(student.getFirstName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setPhoneNumber(student.getPhoneNumber());
                return new ResponseEntity<>(studentRepository.save(existingStudent), HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/patch/{id}")
    @Operation(summary = "Partially update an existing student", description = "Partially update an existing student record")
    public ResponseEntity<Student> patchStudent(@PathVariable Integer id, @RequestBody Student student) {
    return studentRepository.findById(id)
        .map(existingStudent -> {
            if (student.getFirstName() != null) {
                existingStudent.setFirstName(student.getFirstName());
            }
            if (student.getLastName() != null) {
                existingStudent.setLastName(student.getLastName());
            }
            if (student.getEmail() != null) {
                existingStudent.setEmail(student.getEmail());
            }
            if (student.getPhoneNumber() != null) {
                existingStudent.setPhoneNumber(student.getPhoneNumber());
            }
            return new ResponseEntity<>(studentRepository.save(existingStudent), HttpStatus.OK);
        })
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a student by ID", description = "Delete a student record by their ID")
    @Transactional
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        logger.info("Checking if student with id " + id + " exists");
        if (!studentRepository.existsById(id)) {
            logger.warning("Student not found with id " + id);
            return new ResponseEntity<>("Student not found with id " + id, HttpStatus.NOT_FOUND);
        }
        studentRepository.deleteById(id);
        return new ResponseEntity<>("Student deleted with id " + id, HttpStatus.OK);
    }
}