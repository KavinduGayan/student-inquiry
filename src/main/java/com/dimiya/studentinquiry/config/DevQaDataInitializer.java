package com.dimiya.studentinquiry.config;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "qa"})
@RequiredArgsConstructor
public class DevQaDataInitializer {

    private static final String DEFAULT_STUDENT_EMAIL = "student@example.com";
    private static final String DEFAULT_COURSE_CODE = "COURSE-101";
    private static final String DEFAULT_LECTURER_CODE = "LECT-001";

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    @Bean
    CommandLineRunner seedDefaultData() {
        return args -> {
            seedStudent();
            seedCourse();
            seedLecturer();
        };
    }

    private void seedStudent() {
        if (studentRepository.findByEmail(DEFAULT_STUDENT_EMAIL).isPresent()) {
            return;
        }

        studentRepository.save(Student.builder()
                .name("Default Student")
                .email(DEFAULT_STUDENT_EMAIL)
                .build());
    }

    private void seedCourse() {
        if (courseRepository.findByCourseCode(DEFAULT_COURSE_CODE).isPresent()) {
            return;
        }

        courseRepository.save(Course.builder()
                .courseCode(DEFAULT_COURSE_CODE)
                .name("Introduction to Inquiry")
                .build());
    }

    private void seedLecturer() {
        if (lecturerRepository.findByLecturerCode(DEFAULT_LECTURER_CODE).isPresent()) {
            return;
        }

        lecturerRepository.save(Lecturer.builder()
                .lecturerCode(DEFAULT_LECTURER_CODE)
                .name("Default Lecturer")
                .email("lecturer@example.com")
                .build());
    }
}

