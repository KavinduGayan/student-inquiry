package com.dimiya.studentinquiry.domain.inquiry.service;

import com.dimiya.studentinquiry.domain.inquiry.dto.AddInquiryResponseRequest;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryResponse;
import com.dimiya.studentinquiry.domain.inquiry.model.InquiryStatus;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryResponseRepository;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.exception.BadRequestException;
import com.dimiya.studentinquiry.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryResponseService {

    private static final Logger log = LoggerFactory.getLogger(InquiryResponseService.class);

    private final InquiryItemRepository inquiryItemRepository;
    private final InquiryResponseRepository inquiryResponseRepository;
    private final LecturerRepository lecturerRepository;

    public InquiryResponseService(
            InquiryItemRepository inquiryItemRepository,
            InquiryResponseRepository inquiryResponseRepository,
            LecturerRepository lecturerRepository
    ) {
        this.inquiryItemRepository = inquiryItemRepository;
        this.inquiryResponseRepository = inquiryResponseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional
    public void addResponse(Long inquiryItemId, AddInquiryResponseRequest request) {
        Long lecturerId = request.getLecturerId();

        log.info("Adding inquiry response: inquiryItemId={}, lecturerId={}", inquiryItemId, lecturerId);
        log.debug("Inquiry response message length={}", request.getMessage() == null ? 0 : request.getMessage().length());

        InquiryItem item = inquiryItemRepository.findById(inquiryItemId)
                .orElseThrow(() -> {
                    log.warn("Inquiry item not found: inquiryItemId={}", inquiryItemId);
                    return new ResourceNotFoundException("Inquiry item not found: " + inquiryItemId);
                });

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> {
                    log.warn("Lecturer not found: lecturerId={}", lecturerId);
                    return new ResourceNotFoundException("Lecturer not found: " + lecturerId);
                });

        Long assignedLecturerId = item.getLecturer() != null ? item.getLecturer().getId() : null;
        if (assignedLecturerId == null || !assignedLecturerId.equals(lecturer.getId())) {
            log.warn("Lecturer not assigned to inquiry item: inquiryItemId={}, assignedLecturerId={}, lecturerId={}",
                    inquiryItemId, assignedLecturerId, lecturerId);
            throw new BadRequestException("Lecturer not assigned to this inquiry");
        }

        InquiryResponse response = InquiryResponse.builder()
                .inquiryItem(item)
                .lecturer(lecturer)
                .message(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        inquiryResponseRepository.save(response);
        log.info("Inquiry response saved: inquiryItemId={}, lecturerId={}", inquiryItemId, lecturerId);

        InquiryStatus previousStatus = item.getStatus();
        item.setStatus(InquiryStatus.RESPONDED);
        inquiryItemRepository.save(item);

        log.info("Inquiry item status updated: inquiryItemId={}, {} -> {}",
                inquiryItemId, previousStatus, InquiryStatus.RESPONDED);
    }
}

