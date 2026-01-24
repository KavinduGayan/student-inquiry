package com.dimiya.studentinquiry.domain.course.controller;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.service.CourseService;

import java.util.List;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> create(@Valid  @RequestBody Course course) {
        log.info("Create course request received name={}", course.getName());
        log.debug("Create course payload={}", course);

        Course saved = courseService.create(course);

        log.info("Course created id={}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        log.info("Fetch course by id={}", id);

        Course course = courseService.getById(id);

        log.info("Course fetched id={}", id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        log.info("Fetch all courses request received");

        List<Course> courses = courseService.getAll();

        log.info("Fetched all courses count={}", courses.size());
        return ResponseEntity.ok(courses);
    }
}

