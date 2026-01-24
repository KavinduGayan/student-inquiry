package com.dimiya.studentinquiry.domain.inquiry.service;


import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.domain.inquiry.dto.CreateInquiryRequest;
import com.dimiya.studentinquiry.domain.inquiry.entity.Inquiry;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.model.InquiryStatus;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryRepository;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.repo.StudentRepository;
import com.dimiya.studentinquiry.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryService {

    private static final Logger log = LoggerFactory.getLogger(InquiryService.class);

    private final InquiryRepository inquiryRepository;
    private final InquiryItemRepository inquiryItemRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    public InquiryService(
            InquiryRepository inquiryRepository,
            InquiryItemRepository inquiryItemRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            LecturerRepository lecturerRepository
    ) {
        this.inquiryRepository = inquiryRepository;
        this.inquiryItemRepository = inquiryItemRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional
    public Inquiry createInquiry(CreateInquiryRequest request) {
        Long studentId = request.getStudentId();
        int itemCount = request.getItems() == null ? 0 : request.getItems().size();

        log.info("Creating inquiry: studentId={}, itemCount={}", studentId, itemCount);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.warn("Student not found: studentId={}", studentId);
                    return new ResourceNotFoundException("Student not found: " + studentId);
                });

        Inquiry inquiry = Inquiry.builder()
                .student(student)
                .createdAt(LocalDateTime.now())
                .build();

        inquiry = inquiryRepository.save(inquiry);
        log.info("Inquiry created: inquiryId={}, studentId={}", inquiry.getId(), studentId);

        if (request.getItems() == null || request.getItems().isEmpty()) {
            log.warn("Inquiry created with no items: inquiryId={}, studentId={}", inquiry.getId(), studentId);
            return inquiry;
        }

        int savedItems = 0;

        for (var itemReq : request.getItems()) {
            Long courseId = itemReq.getCourseId();
            Long lecturerId = itemReq.getLecturerId();

            log.debug("Creating inquiry item: inquiryId={}, courseId={}, lecturerId={}",
                    inquiry.getId(), courseId, lecturerId);

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> {
                        log.warn("Course not found: courseId={}", courseId);
                        return new ResourceNotFoundException("Course not found: " + courseId);
                    });

            Lecturer lecturer = lecturerRepository.findById(lecturerId)
                    .orElseThrow(() -> {
                        log.warn("Lecturer not found: lecturerId={}", lecturerId);
                        return new ResourceNotFoundException("Lecturer not found: " + lecturerId);
                    });

            InquiryItem item = InquiryItem.builder()
                    .inquiry(inquiry)
                    .course(course)
                    .lecturer(lecturer)
                    .inquiredAt(LocalDateTime.now())
                    .status(InquiryStatus.OPEN)
                    .build();

            InquiryItem saved = inquiryItemRepository.save(item);
            savedItems++;

            log.info("Inquiry item created: inquiryItemId={}, inquiryId={}, lecturerId={}, courseId={}, status={}",
                    saved.getId(), inquiry.getId(), lecturerId, courseId, saved.getStatus());
        }

        log.info("Inquiry creation completed: inquiryId={}, studentId={}, itemsSaved={}",
                inquiry.getId(), studentId, savedItems);

        return inquiry;
    }

    @Transactional(readOnly = true)
    public Page<InquiryItem> getLecturerInquiries(Long lecturerId, Pageable pageable) {
        log.info("Fetching lecturer inquiries: lecturerId={}, page={}, size={}, sort={}",
                lecturerId,
                pageable == null ? null : pageable.getPageNumber(),
                pageable == null ? null : pageable.getPageSize(),
                pageable == null ? null : pageable.getSort());

        Page<InquiryItem> page = inquiryItemRepository.findByLecturerIdOrderByInquiredAtDesc(lecturerId, pageable);

        log.info("Lecturer inquiries fetched: lecturerId={}, returned={}, totalElements={}",
                lecturerId, page.getNumberOfElements(), page.getTotalElements());

        return page;
    }
}
