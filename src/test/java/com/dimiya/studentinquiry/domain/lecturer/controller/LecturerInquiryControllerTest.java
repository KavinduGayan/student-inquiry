package com.dimiya.studentinquiry.domain.lecturer.controller;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.domain.inquiry.entity.Inquiry;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.model.InquiryStatus;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryRepository;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.repo.StudentRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LecturerInquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryItemRepository inquiryItemRepository;

    @BeforeEach
    void setUp() {
        inquiryItemRepository.deleteAll();
        inquiryRepository.deleteAll();
        courseRepository.deleteAll();
        lecturerRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Test
    void getLecturerInquiries() throws Exception {
        Student student = studentRepository.save(Student.builder().name("Alice").email("alice@example.com").build());
        Course course = courseRepository.save(Course.builder().courseCode("BIO101").name("Biology").build());
        Lecturer lecturer = lecturerRepository.save(Lecturer.builder()
                .lecturerCode("LEC100")
                .name("Dr. Smith")
                .email("smith@example.com")
                .build());

        Inquiry inquiry = inquiryRepository.save(Inquiry.builder()
                .student(student)
                .createdAt(LocalDateTime.of(2024, 1, 1, 10, 15, 30))
                .build());

        InquiryItem item = inquiryItemRepository.save(InquiryItem.builder()
                .inquiry(inquiry)
                .course(course)
                .lecturer(lecturer)
                .inquiredAt(LocalDateTime.of(2024, 1, 2, 11, 22, 33))
                .status(InquiryStatus.OPEN)
                .build());

        mockMvc.perform(get("/api/v1/lecturers/{id}/inquiries", lecturer.getId())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].inquiryItemId").value(item.getId()))
                .andExpect(jsonPath("$.content[0].studentName").value("Alice"))
                .andExpect(jsonPath("$.content[0].courseName").value("Biology"))
                .andExpect(jsonPath("$.content[0].inquiredAt").value("2024-01-02T11:22:33"))
                .andExpect(jsonPath("$.content[0].status").value("OPEN"));
    }
}
