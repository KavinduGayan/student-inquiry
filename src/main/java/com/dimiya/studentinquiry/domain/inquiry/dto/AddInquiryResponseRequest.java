package com.dimiya.studentinquiry.domain.inquiry.dto;

public class AddInquiryResponseRequest {

    private Long lecturerId;

    private String message;

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

