package com.dimiya.studentinquiry.domain.inquiry.entity;

import com.dimiya.studentinquiry.domain.inquiry.model.InquiryStatus;
import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry_items")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Inquiry inquiry;

    @ManyToOne(optional = false)
    private Course course;

    @ManyToOne(optional = false)
    private Lecturer lecturer;

    @Column(nullable = false)
    private LocalDateTime inquiredAt;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;
}

