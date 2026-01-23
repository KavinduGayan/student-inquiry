package com.dimiya.studentinquiry.domain.inquiry.dto;


import java.time.LocalDateTime;
import java.util.List;

public class CreateInquiryResponse {

    private Long inquiryId;
    private String studentName;
    private LocalDateTime createdAt;
    private List<InquiryResponseView> items;

    public Long getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<InquiryResponseView> getItems() {
        return items;
    }

    public void setItems(List<InquiryResponseView> items) {
        this.items = items;
    }
}

