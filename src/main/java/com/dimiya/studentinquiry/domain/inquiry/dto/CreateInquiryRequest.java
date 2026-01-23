package com.dimiya.studentinquiry.domain.inquiry.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateInquiryRequest {

    @NotNull
    private Long studentId;

    @NotEmpty
    @Valid
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

