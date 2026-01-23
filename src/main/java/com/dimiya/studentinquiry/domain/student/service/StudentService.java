package com.dimiya.studentinquiry.domain.student.service;

import com.dimiya.studentinquiry.domain.student.entity.Student;
import com.dimiya.studentinquiry.domain.student.repo.StudentRepository;
import com.dimiya.studentinquiry.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}

