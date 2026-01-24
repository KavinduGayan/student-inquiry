package com.dimiya.studentinquiry.domain.student.controller;

import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.service.StudentService;

import java.util.List;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        log.info("POST /students - create student request received");
        log.debug("Create student payload: name={}", student.getName());

        Student saved = studentService.create(student);

        log.info("POST /students - student created successfully, id={}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        log.info("GET /students/{} - fetch student by id", id);

        Student student = studentService.getById(id);

        log.info("GET /students/{} - student found", id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        log.info("GET /students - fetch all students");

        List<Student> students = studentService.getAll();

        log.info("GET /students - {} students returned", students.size());
        return ResponseEntity.ok(students);
    }
}

