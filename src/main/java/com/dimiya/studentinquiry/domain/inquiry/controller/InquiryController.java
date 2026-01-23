package com.dimiya.studentinquiry.domain.inquiry.controller;

import com.dimiya.studentinquiry.domain.inquiry.dto.CreateInquiryRequest;
import com.dimiya.studentinquiry.domain.inquiry.dto.CreateInquiryResponse;
import com.dimiya.studentinquiry.domain.inquiry.dto.InquiryResponseView;
import com.dimiya.studentinquiry.domain.inquiry.entity.Inquiry;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.service.InquiryService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;
    private final InquiryItemRepository inquiryItemRepository;

    public InquiryController(InquiryService inquiryService, InquiryItemRepository inquiryItemRepository) {
        this.inquiryService = inquiryService;
        this.inquiryItemRepository = inquiryItemRepository;
    }

    @PostMapping
    public ResponseEntity<CreateInquiryResponse> create(@RequestBody CreateInquiryRequest request) {
        Inquiry inquiry = inquiryService.createInquiry(request);

        // fetch items for this inquiry (simple approach)
        List<InquiryItem> items = inquiryItemRepository.findAll()
                .stream()
                .filter(ii -> ii.getInquiry().getId().equals(inquiry.getId()))
                .collect(Collectors.toList());

        CreateInquiryResponse response = new CreateInquiryResponse();
        response.setInquiryId(inquiry.getId());
        response.setStudentName(inquiry.getStudent().getName());
        response.setCreatedAt(inquiry.getCreatedAt());
        response.setItems(items.stream().map(this::toInquiryView).toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private InquiryResponseView toInquiryView(InquiryItem item) {
        InquiryResponseView v = new InquiryResponseView();
        v.setInquiryItemId(item.getId());
        v.setCourseName(item.getCourse().getName());
        v.setLecturerName(item.getLecturer().getName());

        LocalDate date = item.getInquiredAt().toLocalDate();
        LocalTime time = item.getInquiredAt().toLocalTime().withNano(0);

        v.setInquireDate(date);
        v.setInquireTime(time);
        return v;
    }
}

