package com.dimiya.studentinquiry.domain.inquiry.controller;

import com.dimiya.studentinquiry.domain.inquiry.dto.LecturerInquiryViewResponse;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.service.InquiryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerInquiryController {

    private final InquiryService inquiryService;

    public LecturerInquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("/{lecturerId}/inquiries")
    public ResponseEntity<List<LecturerInquiryViewResponse>> getLecturerInquiries(@PathVariable Long lecturerId) {
        List<InquiryItem> items = inquiryService.getLecturerInquiries(lecturerId);

        List<LecturerInquiryViewResponse> response = items.stream().map(ii -> {
            LecturerInquiryViewResponse r = new LecturerInquiryViewResponse();
            r.setInquiryItemId(ii.getId());
            r.setStudentName(ii.getInquiry().getStudent().getName());
            r.setCourseName(ii.getCourse().getName());
            r.setInquiredAt(ii.getInquiredAt());
            r.setStatus(ii.getStatus() != null ? ii.getStatus().name() : null);
            return r;
        }).toList();

        return ResponseEntity.ok(response);
    }
}
