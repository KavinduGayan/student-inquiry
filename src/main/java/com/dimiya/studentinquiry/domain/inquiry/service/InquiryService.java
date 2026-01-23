package com.dimiya.studentinquiry.domain.inquiry.service;


import com.dimiya.studentinquiry.domain.course.entity.Course;
import com.dimiya.studentinquiry.domain.course.repo.CourseRepository;
import com.dimiya.studentinquiry.domain.inquiry.dto.CreateInquiryRequest;
import com.dimiya.studentinquiry.domain.inquiry.entity.Inquiry;
import com.dimiya.studentinquiry.domain.inquiry.entity.InquiryItem;
import com.dimiya.studentinquiry.domain.inquiry.model.InquiryStatus;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryItemRepository;
import com.dimiya.studentinquiry.domain.inquiry.repo.InquiryRepository;
import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.repo.StudentRepository;
import com.dimiya.studentinquiry.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryItemRepository inquiryItemRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    public InquiryService(
            InquiryRepository inquiryRepository,
            InquiryItemRepository inquiryItemRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            LecturerRepository lecturerRepository
    ) {
        this.inquiryRepository = inquiryRepository;
        this.inquiryItemRepository = inquiryItemRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional
    public Inquiry createInquiry(CreateInquiryRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Inquiry inquiry = Inquiry.builder()
                .student(student)
                .createdAt(LocalDateTime.now())
                .build();

        inquiry = inquiryRepository.save(inquiry);

        for (var itemReq : request.getItems()) {

            Course course = courseRepository.findById(itemReq.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            Lecturer lecturer = lecturerRepository.findById(itemReq.getLecturerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));

            InquiryItem item = InquiryItem.builder()
                    .inquiry(inquiry)
                    .course(course)
                    .lecturer(lecturer)
                    .inquiredAt(LocalDateTime.now())
                    .status(InquiryStatus.OPEN)
                    .build();

            inquiryItemRepository.save(item);
        }

        return inquiry;
    }

    public List<InquiryItem> getLecturerInquiries(Long lecturerId) {
        return inquiryItemRepository.findByLecturerIdOrderByInquiredAtDesc(lecturerId);
    }
}
