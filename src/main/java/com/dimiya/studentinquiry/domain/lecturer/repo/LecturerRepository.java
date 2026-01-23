package com.dimiya.studentinquiry.domain.lecturer.repo;


import java.util.Optional;

import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    Optional<Lecturer> findByLecturerCode(String lecturerCode);

    Optional<Lecturer> findByEmail(String email);

    boolean existsByLecturerCode(String lecturerCode);

    boolean existsByEmail(String email);
}

