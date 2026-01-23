package com.dimiya.studentinquiry.domain.lecturer.repo;


import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

}

