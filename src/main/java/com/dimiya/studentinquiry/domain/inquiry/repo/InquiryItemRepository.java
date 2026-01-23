package com.dimiya.studentinquiry.domain.inquiry.repo;

import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryItemRepository extends JpaRepository<InquiryItem, Long> {

    Page<InquiryItem> findByLecturerIdOrderByInquiredAtDesc(Long lecturerId, Pageable pageable);
}

