package com.dimiya.studentinquiry.domain.inquiry.controller;

import com.dimiya.studentinquiry.domain.inquiry.dto.LecturerInquiryViewResponse;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.service.InquiryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerInquiryController {

    private static final Logger log = LoggerFactory.getLogger(LecturerInquiryController.class);

    private final InquiryService inquiryService;

    public LecturerInquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("/{lecturerId}/inquiries")
    public ResponseEntity<Page<LecturerInquiryViewResponse>> getLecturerInquiries(
            @PathVariable Long lecturerId,
            Pageable pageable
    ) {
        log.info(
                "Fetch lecturer inquiries request received lecturerId={} page={} size={}",
                lecturerId,
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<InquiryItem> items = inquiryService.getLecturerInquiries(lecturerId, pageable);

        Page<LecturerInquiryViewResponse> response = items.map(ii -> {
            LecturerInquiryViewResponse r = new LecturerInquiryViewResponse();
            r.setInquiryItemId(ii.getId());
            r.setStudentName(ii.getInquiry().getStudent().getName());
            r.setCourseName(ii.getCourse().getName());
            r.setInquiredAt(ii.getInquiredAt());
            r.setStatus(ii.getStatus() != null ? ii.getStatus().name() : null);
            return r;
        });

        log.info(
                "Fetched lecturer inquiries lecturerId={} totalElements={} totalPages={}",
                lecturerId,
                response.getTotalElements(),
                response.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }


}
