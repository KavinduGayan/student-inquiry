package com.dimiya.studentinquiry.domain.inquiry.repo;


import java.util.List;

import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryItemRepository extends JpaRepository<InquiryItem, Long> {

    List<InquiryItem> findByLecturerIdOrderByInquiredAtDesc(Long lecturerId);
}

