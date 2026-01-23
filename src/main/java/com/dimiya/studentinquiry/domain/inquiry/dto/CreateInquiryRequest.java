package com.dimiya.studentinquiry.domain.inquiry.dto;


import java.util.List;

public class CreateInquiryRequest {


    private Long studentId;

    private List<InquiryItemRequest> items;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<InquiryItemRequest> getItems() {
        return items;
    }

    public void setItems(List<InquiryItemRequest> items) {
        this.items = items;
    }
}

