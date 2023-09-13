package com.gfstechnology.msvcstudent.services;

import com.gfstechnology.msvcstudent.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAllStudent();
    Optional<Student> findStudentById(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);

    Optional<Student> findStudentByEmail(String email);
}
