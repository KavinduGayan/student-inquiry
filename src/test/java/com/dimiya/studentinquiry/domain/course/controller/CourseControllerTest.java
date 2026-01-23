package com.dimiya.studentinquiry.domain.course.controller;

import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryRepository;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InquiryItemRepository inquiryItemRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @BeforeEach
    void setUp() {
        inquiryItemRepository.deleteAll();
        inquiryRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void createCourse() throws Exception {
        Map<String, Object> payload = Map.of(
                "courseCode", "BIO101",
                "name", "Biology"
        );

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.courseCode").value("BIO101"))
                .andExpect(jsonPath("$.name").value("Biology"));
    }

    @Test
    void getCourseById() throws Exception {
        Course course = courseRepository.save(Course.builder()
                .courseCode("CHEM201")
                .name("Chemistry")
                .build());

        mockMvc.perform(get("/api/v1/courses/{id}", course.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.courseCode").value("CHEM201"))
                .andExpect(jsonPath("$.name").value("Chemistry"));
    }

    @Test
    void getAllCourses() throws Exception {
        courseRepository.save(Course.builder().courseCode("BIO101").name("Biology").build());
        courseRepository.save(Course.builder().courseCode("CHEM201").name("Chemistry").build());

        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].courseCode", containsInAnyOrder("BIO101", "CHEM201")));
    }
}
