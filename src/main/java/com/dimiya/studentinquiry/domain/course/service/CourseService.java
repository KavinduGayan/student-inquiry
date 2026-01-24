package com.dimiya.studentinquiry.domain.course.service;


import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.exception.ResourceNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course create(Course course) {
        log.info("Creating course with code={}", course.getCourseCode());
        log.debug("Course create payload: name={}", course.getName());

        Course saved = courseRepository.save(course);

        log.info("Course created successfully, id={}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public Course getById(Long id) {
        log.info("Fetching course by id={}", id);

        return courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course not found for id={}", id);
                    return new ResourceNotFoundException("Course not found: " + id);
                });
    }

    @Transactional(readOnly = true)
    public List<Course> getAll() {
        log.info("Fetching all courses");

        List<Course> courses = courseRepository.findAll();

        log.info("Fetched {} courses", courses.size());
        return courses;
    }
}

