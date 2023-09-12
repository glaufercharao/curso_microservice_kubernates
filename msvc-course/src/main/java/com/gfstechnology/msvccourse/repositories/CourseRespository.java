package com.gfstechnology.msvccourse.repositories;

import com.gfstechnology.msvccourse.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRespository extends CrudRepository<Course, Long> {
}
