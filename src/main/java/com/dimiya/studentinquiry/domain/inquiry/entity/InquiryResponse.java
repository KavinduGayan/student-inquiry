package com.dimiya.studentinquiry.domain.inquiry.entity;

import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private InquiryItem inquiryItem;

    @ManyToOne(optional = false)
    private Lecturer lecturer;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sentAt;
}

