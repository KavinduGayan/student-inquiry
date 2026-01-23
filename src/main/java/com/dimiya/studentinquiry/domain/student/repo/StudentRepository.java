package com.dimiya.studentinquiry.domain.student.repo;
import java.util.Optional;

import com.dimiya.studentinquiry.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

