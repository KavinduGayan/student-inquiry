package com.dimiya.studentinquiry.domain.inquiry.repo;


import java.util.List;

import com.dimiya.studentinquiry.domain.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}

