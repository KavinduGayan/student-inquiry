package com.dimiya.studentinquiry.domain.lecturer.controller;

import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.service.LecturerService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<Lecturer> create(@RequestBody Lecturer lecturer) {
        Lecturer saved = lecturerService.create(lecturer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lecturerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Lecturer>> getAll() {
        return ResponseEntity.ok(lecturerService.getAll());
    }
}

