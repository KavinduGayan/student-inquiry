package com.dimiya.studentinquiry.domain.inquiry.repo;


import java.time.LocalDateTime;
import java.util.List;

import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InquiryItemRepository extends JpaRepository<InquiryItem, Long> {

    List<InquiryItem> findByLecturerIdOrderByInquiredAtDesc(Long lecturerId);

    @Query("""
        select ii
        from InquiryItem ii
        where ii.lecturer.id = :lecturerId
          and ii.inquiredAt between :from and :to
        order by ii.inquiredAt desc
        """)
    List<InquiryItem> findLecturerItemsBetween(
            @Param("lecturerId") Long lecturerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}

