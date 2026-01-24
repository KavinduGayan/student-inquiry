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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inquiries")
public class InquiryController {

    private static final Logger log = LoggerFactory.getLogger(InquiryController.class);

    private final InquiryService inquiryService;
    private final InquiryItemRepository inquiryItemRepository;

    public InquiryController(InquiryService inquiryService, InquiryItemRepository inquiryItemRepository) {
        this.inquiryService = inquiryService;
        this.inquiryItemRepository = inquiryItemRepository;
    }

    @PostMapping
    public ResponseEntity<CreateInquiryResponse> create(@RequestBody CreateInquiryRequest request) {
        int itemCount = request.getItems() == null ? 0 : request.getItems().size();
        log.info("Create inquiry request received studentId={} itemCount={}", request.getStudentId(), itemCount);
        log.debug("Create inquiry payload items={}", request.getItems());

        Inquiry inquiry = inquiryService.createInquiry(request);

        // fetch items for this inquiry (simple approach)
        List<InquiryItem> items = inquiryItemRepository.findAll()
                .stream()
                .filter(ii -> ii.getInquiry().getId().equals(inquiry.getId()))
                .toList();

        CreateInquiryResponse response = new CreateInquiryResponse();
        response.setInquiryId(inquiry.getId());
        response.setStudentName(inquiry.getStudent().getName());
        response.setCreatedAt(inquiry.getCreatedAt());
        response.setItems(items.stream().map(this::toInquiryView).toList());

        log.info("Inquiry created inquiryId={} responseItemCount={}", inquiry.getId(), response.getItems().size());
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

