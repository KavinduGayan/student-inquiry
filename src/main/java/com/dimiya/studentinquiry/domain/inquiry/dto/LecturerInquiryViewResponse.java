package com.dimiya.studentinquiry.domain.inquiry.dto;


import java.time.LocalDateTime;

public class LecturerInquiryViewResponse {

    private Long inquiryItemId;
    private String studentName;
    private String courseName;
    private LocalDateTime inquiredAt;
    private String status;

    public Long getInquiryItemId() {
        return inquiryItemId;
    }

    public void setInquiryItemId(Long inquiryItemId) {
        this.inquiryItemId = inquiryItemId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDateTime getInquiredAt() {
        return inquiredAt;
    }

    public void setInquiredAt(LocalDateTime inquiredAt) {
        this.inquiredAt = inquiredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

