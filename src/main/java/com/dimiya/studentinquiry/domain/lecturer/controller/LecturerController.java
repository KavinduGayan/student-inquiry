package com.dimiya.studentinquiry.domain.lecturer.controller;

import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.service.LecturerService;

import java.util.List;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerController {

    private static final Logger log = LoggerFactory.getLogger(LecturerController.class);

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<Lecturer> create(@Valid @RequestBody Lecturer lecturer) {
        log.info("POST /lecturers - create lecturer request received");
        log.debug("Create lecturer payload: lecturerCode={}, email={}",
                lecturer.getLecturerCode(), lecturer.getEmail());

        Lecturer saved = lecturerService.create(lecturer);

        log.info("POST /lecturers - lecturer created successfully, id={}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getById(@PathVariable Long id) {
        log.info("GET /lecturers/{} - fetch lecturer by id", id);

        Lecturer lecturer = lecturerService.getById(id);

        log.info("GET /lecturers/{} - lecturer found", id);
        return ResponseEntity.ok(lecturer);
    }

    @GetMapping
    public ResponseEntity<List<Lecturer>> getAll() {
        log.info("GET /lecturers - fetch all lecturers");

        List<Lecturer> lecturers = lecturerService.getAll();

        log.info("GET /lecturers - {} lecturers returned", lecturers.size());
        return ResponseEntity.ok(lecturers);
    }
}

