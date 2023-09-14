package com.gfstechnology.msvcstudent.services;

import com.gfstechnology.msvcstudent.entities.Student;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAllStudent();
    Optional<Student> findStudentById(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    List<Student> findByIds(Iterable<Long> ids);
    Optional<Student> findStudentByEmail(String email);
}
