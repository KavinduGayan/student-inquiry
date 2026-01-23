package com.dimiya.studentinquiry.domain.inquiry.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class InquiryResponseView {

    private Long inquiryItemId;
    private String courseName;
    private String lecturerName;
    private LocalDate inquireDate;
    private LocalTime inquireTime;

    public Long getInquiryItemId() {
        return inquiryItemId;
    }

    public void setInquiryItemId(Long inquiryItemId) {
        this.inquiryItemId = inquiryItemId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public LocalDate getInquireDate() {
        return inquireDate;
    }

    public void setInquireDate(LocalDate inquireDate) {
        this.inquireDate = inquireDate;
    }

    public LocalTime getInquireTime() {
        return inquireTime;
    }

    public void setInquireTime(LocalTime inquireTime) {
        this.inquireTime = inquireTime;
    }
}

