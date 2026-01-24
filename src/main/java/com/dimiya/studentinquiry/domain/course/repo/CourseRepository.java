package com.dimiya.studentinquiry.domain.course.repo;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
}

