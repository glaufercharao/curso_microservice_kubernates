package com.gfstechnology.msvcstudent.services.impl;

import com.gfstechnology.msvcstudent.client.CourseClientRest;
import com.gfstechnology.msvcstudent.entities.Student;
import com.gfstechnology.msvcstudent.repositories.StudentRepository;
import com.gfstechnology.msvcstudent.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseClientRest courseClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllStudent() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Optional<Student> student = findStudentById(id);
        student.orElseThrow(() -> new RuntimeException("Student not found "));
        studentRepository.deleteById(id);
        courseClientRest.removeCourseStudentById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByIds(Iterable<Long> ids) {
        return (List<Student>) studentRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
