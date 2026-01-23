package com.dimiya.studentinquiry.domain.inquiry.controller;

import com.dimiya.studentinquiry.domain.inquiry.dto.AddInquiryResponseRequest;
import com.dimiya.studentinquiry.domain.inquiry.service.InquiryResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inquiry-items")
public class InquiryResponseController {

    private final InquiryResponseService inquiryResponseService;

    public InquiryResponseController(InquiryResponseService inquiryResponseService) {
        this.inquiryResponseService = inquiryResponseService;
    }

    @PostMapping("/{inquiryItemId}/responses")
    public ResponseEntity<Void> addResponse(
            @PathVariable Long inquiryItemId,
            @RequestBody AddInquiryResponseRequest request
    ) {
        inquiryResponseService.addResponse(inquiryItemId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

