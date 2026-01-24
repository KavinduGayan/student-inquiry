package com.dimiya.studentinquiry.domain.lecturer.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Generated lecturer identifier")
    private Long id;

    @Column(nullable = false, unique = true)
    private String lecturerCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Email
    @NotBlank
    private String email;
}

