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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryResponseService {

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

        InquiryItem item = inquiryItemRepository.findById(inquiryItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry item not found"));

        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));

        if (!item.getLecturer().getId().equals(lecturer.getId())) {
            throw new BadRequestException("Lecturer not assigned to this inquiry");
        }

        InquiryResponse response = InquiryResponse.builder()
                .inquiryItem(item)
                .lecturer(lecturer)
                .message(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        inquiryResponseRepository.save(response);

        item.setStatus(InquiryStatus.RESPONDED);
        inquiryItemRepository.save(item);
    }
}

