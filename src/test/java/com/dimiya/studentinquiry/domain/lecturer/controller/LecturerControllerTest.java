package com.dimiya.studentinquiry.domain.lecturer.controller;

import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryRepository;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LecturerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private InquiryItemRepository inquiryItemRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @BeforeEach
    void setUp() {
        inquiryItemRepository.deleteAll();
        inquiryRepository.deleteAll();
        lecturerRepository.deleteAll();
    }

    @Test
    void createLecturer() throws Exception {
        Map<String, Object> payload = Map.of(
                "lecturerCode", "LEC100",
                "name", "Dr. Smith",
                "email", "smith@example.com"
        );

        mockMvc.perform(post("/api/v1/lecturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.lecturerCode").value("LEC100"))
                .andExpect(jsonPath("$.name").value("Dr. Smith"))
                .andExpect(jsonPath("$.email").value("smith@example.com"));
    }

    @Test
    void getLecturerById() throws Exception {
        Lecturer lecturer = lecturerRepository.save(Lecturer.builder()
                .lecturerCode("LEC200")
                .name("Dr. Jones")
                .email("jones@example.com")
                .build());

        mockMvc.perform(get("/api/v1/lecturers/{id}", lecturer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lecturer.getId()))
                .andExpect(jsonPath("$.lecturerCode").value("LEC200"))
                .andExpect(jsonPath("$.name").value("Dr. Jones"))
                .andExpect(jsonPath("$.email").value("jones@example.com"));
    }

    @Test
    void getAllLecturers() throws Exception {
        lecturerRepository.save(Lecturer.builder().lecturerCode("LEC100").name("Dr. Smith").email("smith@example.com").build());
        lecturerRepository.save(Lecturer.builder().lecturerCode("LEC200").name("Dr. Jones").email("jones@example.com").build());

        mockMvc.perform(get("/api/v1/lecturers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].lecturerCode", containsInAnyOrder("LEC100", "LEC200")));
    }
}
