package com.dimiya.studentinquiry.domain.course.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Generated course identifier")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String courseCode;

    @Column(nullable = false)
    @NotBlank
    private String name;
}
