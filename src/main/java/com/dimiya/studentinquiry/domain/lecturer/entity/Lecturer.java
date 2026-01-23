package com.dimiya.studentinquiry.domain.lecturer.entity;

import jakarta.persistence.*;
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
    private Long id;

    @Column(nullable = false, unique = true)
    private String lecturerCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}

