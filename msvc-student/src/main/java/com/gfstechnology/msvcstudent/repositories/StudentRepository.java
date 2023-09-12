package com.gfstechnology.msvcstudent.repositories;

import com.gfstechnology.msvcstudent.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
