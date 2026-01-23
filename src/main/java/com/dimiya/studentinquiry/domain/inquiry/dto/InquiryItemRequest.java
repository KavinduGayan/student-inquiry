package com.dimiya.studentinquiry.domain.inquiry.dto;

import jakarta.validation.constraints.NotNull;

public class InquiryItemRequest {

    @NotNull
    private Long courseId;

    @NotNull
    private Long lecturerId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }
}

