package com.dimiya.studentinquiry.domain.inquiry.controller;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.domain.inquiry.dto.CreateInquiryRequest;
import com.dimiya.studentinquiry.domain.inquiry.dto.InquiryItemRequest;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryRepository;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.repo.StudentRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

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
    void createInquiry() throws Exception {
        Student student = studentRepository.save(Student.builder().name("Alice").build());
        Course course = courseRepository.save(Course.builder().courseCode("BIO101").name("Biology").build());
        Lecturer lecturer = lecturerRepository.save(Lecturer.builder()
                .lecturerCode("LEC100")
                .name("Dr. Smith")
                .email("smith@example.com")
                .build());

        CreateInquiryRequest request = new CreateInquiryRequest();
        request.setStudentId(student.getId());
        InquiryItemRequest itemRequest = new InquiryItemRequest();
        itemRequest.setCourseId(course.getId());
        itemRequest.setLecturerId(lecturer.getId());
        request.setItems(List.of(itemRequest));

        mockMvc.perform(post("/api/v1/inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.inquiryId").isNumber())
                .andExpect(jsonPath("$.studentName").value("Alice"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.items[0].inquiryItemId").isNumber())
                .andExpect(jsonPath("$.items[0].courseName").value("Biology"))
                .andExpect(jsonPath("$.items[0].lecturerName").value("Dr. Smith"))
                .andExpect(jsonPath("$.items[0].inquireDate").isNotEmpty())
                .andExpect(jsonPath("$.items[0].inquireTime").isNotEmpty());

        assertThat(inquiryRepository.count()).isEqualTo(1);
        assertThat(inquiryItemRepository.count()).isEqualTo(1);
    }
}
