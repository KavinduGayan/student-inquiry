package com.dimiya.studentinquiry.domain.course.repo;


import java.util.Optional;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);
}

