package com.gfstechnology.msvccourse.repositories;

import com.gfstechnology.msvccourse.models.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRespository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("delete from CourseStudent  cs where cs.studentId=?1")
    void removeCourseStudentById(Long id);
}
