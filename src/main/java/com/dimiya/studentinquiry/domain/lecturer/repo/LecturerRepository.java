package com.dimiya.studentinquiry.domain.lecturer.repo;


import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    Optional<Lecturer> findByLecturerCode(String lecturerCode);
}

