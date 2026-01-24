package com.dimiya.studentinquiry.domain.lecturer.service;

import com.dimiya.studentinquiry.domain.lecturer.entity.Lecturer;
import com.dimiya.studentinquiry.domain.lecturer.repo.LecturerRepository;
import com.dimiya.studentinquiry.exception.ResourceNotFoundException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LecturerService {

    private static final Logger log = LoggerFactory.getLogger(LecturerService.class);

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional
    public Lecturer create(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    @Transactional(readOnly = true)
    public Lecturer getById(Long id) {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Lecturer> getAll() {
        return lecturerRepository.findAll();
    }
}

