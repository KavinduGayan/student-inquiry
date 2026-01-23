package com.dimiya.studentinquiry.domain.inquiry.repo;


import java.util.List;

import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryResponseRepository extends JpaRepository<InquiryResponse, Long> {

    List<InquiryResponse> findByInquiryItemIdOrderBySentAtAsc(Long inquiryItemId);
}

