package com.gfstechnology.msvcstudent.repositories;

import com.gfstechnology.msvcstudent.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
